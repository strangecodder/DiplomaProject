package com.example.opcservice.service;

import com.commondto.dto.SensorValueDTO;
import com.example.opcservice.dto.ConnectionDTO;
import com.example.opcservice.dto.CreateNodeDTO;
import com.example.opcservice.dto.LogDTO;
import com.example.opcservice.kafka.producer.KafkaSensorDataSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.milo.opcua.sdk.client.AddressSpace;
import org.eclipse.milo.opcua.sdk.client.api.subscriptions.UaMonitoredItem;
import org.eclipse.milo.opcua.sdk.client.api.subscriptions.UaSubscription;
import org.eclipse.milo.opcua.sdk.client.nodes.UaNode;
import org.eclipse.milo.opcua.sdk.client.nodes.UaVariableNode;
import org.eclipse.milo.opcua.sdk.client.OpcUaClient;

import org.eclipse.milo.opcua.stack.core.UaException;
import org.eclipse.milo.opcua.stack.core.types.builtin.*;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.UByte;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.UInteger;
import org.eclipse.milo.opcua.stack.core.types.enumerated.MonitoringMode;
import org.eclipse.milo.opcua.stack.core.types.enumerated.NodeClass;
import org.eclipse.milo.opcua.stack.core.types.enumerated.TimestampsToReturn;
import org.eclipse.milo.opcua.stack.core.types.structured.*;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;


@Service
@RequiredArgsConstructor
@Slf4j
public class OpcUAClientService {

    private final SocketSendService socketSendService;

    private final KafkaSensorDataSender sensorDataSender;

    private ConcurrentHashMap<String,OpcUaClient> opcUaClient = new ConcurrentHashMap<>();
    private boolean isConnected = false;
    private int writeValue = 3;


//    public void connect(ConnectionDTO connectionDTO) {
////        try {
////            opcUaClient = OpcUaClient.create(connectionDTO.getUrl());
////            opcUaClient.connect().get();
////            isConnected = true;
////
////            for (String nodeId : connectionDTO.getNodeList()) {
////                createMonitoredItem(nodeId);
////            }
////            //todo: добавить запрос в бд или редис для построения нод
////        }catch (Exception e) {
////            log.error(e.getMessage());
////        }
//    }

    public void connect(ConnectionDTO connectionDTO) {
        String url = connectionDTO.getUrl();
        try {
            if(!opcUaClient.containsKey(url)) {
                opcUaClient.put(url,OpcUaClient.create(url));
            }
            opcUaClient.get(url).connect().get();

            isConnected = true;

            if(!iaNodeExists(url,connectionDTO.getNodeId())) {
                createNodeByServerNode(new CreateNodeDTO(
                        url,
                        connectionDTO.getNodeId(),
                        null,
                        connectionDTO.getPeriod()
                ));

                return;
            }

            createMonitoredItem(url,connectionDTO.getNodeId(),connectionDTO.getPeriod());

        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public void connect(String url) {
        try {
            if(!opcUaClient.containsKey(url)) {
                opcUaClient.put(url,OpcUaClient.create(url));
            }
            opcUaClient.get(url).connect().get();

        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public void createNode() throws Exception{
        NodeId nodeId = new NodeId(1,"TestNode");
        NodeClass nodeClass = NodeClass.Variable;

        UInteger writeMask = UInteger.valueOf(0);
        UInteger userWriteMask = UInteger.valueOf(0);

        QualifiedName browseName = new QualifiedName(1,"TestNode");

        LocalizedText description = new LocalizedText("en","LLLLLLOOOOOOLLLLL");
        LocalizedText displayName = new LocalizedText("en","TestNode");
        UInteger accessLevel = UInteger.valueOf(3);
        UByte userAccessLevel = UByte.valueOf(3);

        ExpandedNodeId requestedNewNode = ExpandedNodeId.parse( nodeId.toString() );

        ObjectAttributes objectAttributes = new ObjectAttributes(
                writeMask,description,displayName,userWriteMask,accessLevel,userAccessLevel
        );

        ExpandedNodeId parentNodeId = ExpandedNodeId.parse("ns=1;s=RootNode");

        AddNodesItem addNodesItem = new AddNodesItem(
                parentNodeId,
                NodeId.NULL_VALUE,
                requestedNewNode,
                browseName,
                nodeClass,
                null,
                ExpandedNodeId.NULL_VALUE);

        String audit = "";

        RequestHeader requestHeader = new RequestHeader(
                new NodeId(0,"RequestHandler"),
                DateTime.now(),
                UInteger.valueOf(1),
                UInteger.valueOf(0),
                audit,
                UInteger.valueOf(60000),
                null
        );

        AddNodesItem[] items = new AddNodesItem[]{addNodesItem};

        AddNodesRequest addNodesRequest = new AddNodesRequest(requestHeader,items);
    }

    public boolean iaNodeExists(String url, String nodeId) {
    try {
        NodeId nid = new NodeId(1,nodeId);
        UaNode testNodeTry = opcUaClient.get(url).getAddressSpace().getNode(nid);
    }catch (UaException e){
        return false;
    }
    return true;
    }

    public void createNodeByServerNode(CreateNodeDTO createNodeDTO){
        boolean connected = false;
        try {
            if (!opcUaClient.containsKey(createNodeDTO.getUrl())) {
                opcUaClient.put(createNodeDTO.getUrl(), OpcUaClient.create(createNodeDTO.getUrl()));
                opcUaClient.get(createNodeDTO.getUrl()).connect().get();
            }else {
                connected = true;
            }
        }catch (Exception e) {
            log.error(e.getMessage());
        }

        NodeId objectId = new NodeId(1,"MyObject");
        NodeId methodId = new NodeId(1,"addNodesMethod");


        try {
            UaNode testNodeTry = opcUaClient.get(createNodeDTO.getUrl()).getAddressSpace().getNode(objectId);
            if(testNodeTry != null){
                System.out.println("Node already exists");
            }
        } catch (UaException e) {
            throw new RuntimeException(e);
        }

        try {
            UaNode testNodeTry = opcUaClient.get(createNodeDTO.getUrl()).getAddressSpace().getNode(methodId);
            if(testNodeTry != null){
                System.out.println("Node already exists");
            }
        } catch (UaException e) {
            throw new RuntimeException(e);
        }

        Variant[] inputs = new Variant[]{
                new Variant(createNodeDTO.getNodeName()),
                new Variant((createNodeDTO.getDescription()!=null)?createNodeDTO.getDescription():"Default description for "+createNodeDTO.getNodeName())
        };

        CallMethodRequest request = new CallMethodRequest(objectId,methodId,inputs);
        try {
            CompletableFuture<CallMethodResult> result = opcUaClient.get(createNodeDTO.getUrl()).call(request);
            System.out.println("Status code: "+ result.get().getStatusCode());
            //System.out.println(StatusCode.GOOD);
            if(result.get().getStatusCode().equals(StatusCode.GOOD)){
                createMonitoredItem(createNodeDTO.getUrl(),createNodeDTO.getNodeName(),createNodeDTO.getPeriod());
            }
            if(!connected){
                disconnect(createNodeDTO.getUrl());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void readNode(String url, String nodeId) throws Exception {
        NodeId id = new NodeId(1, nodeId);

        AddressSpace addressSpace = opcUaClient.get(url).getAddressSpace();

        UaVariableNode variableNode = opcUaClient.get(url).getAddressSpace().getVariableNode(id);

        String name = variableNode.getDisplayName().getText();
        Variant value = variableNode.getValue().getValue();

        System.out.println("Name: " + name);
        System.out.println("Значение переменной: "+value);

    }

    public void createMonitoredItem(String url, String nodeName,Double period) throws Exception {
        UaSubscription subscription = opcUaClient.get(url).getSubscriptionManager().createSubscription(period).get();

        NodeId nodeId = new NodeId(1, nodeName);

        ReadValueId readValueId = new ReadValueId(nodeId,
                UInteger.valueOf(13),
                null,
                null);

        MonitoringParameters parameters = new MonitoringParameters(
                 UInteger.valueOf(1),
                1.0,
                null,
                null,
                Boolean.TRUE
        );

        MonitoredItemCreateRequest request = new MonitoredItemCreateRequest(
                readValueId,
                MonitoringMode.Reporting,
          parameters
        );

        var requests = Arrays.asList(request);

        subscription.createMonitoredItems(
                TimestampsToReturn.Both,
                requests,
                (item,status)->{
                    System.out.println(status);
                }
        ).get();

        for (UaMonitoredItem item: subscription.getMonitoredItems()) {
            item.setValueConsumer(value->{
                socketSendService.sendMessage(nodeId.getIdentifier().toString(),(Double) value.getValue().getValue());
                socketSendService.sendAnalytic(new LogDTO(nodeId.getIdentifier().toString(),
                        (Double) value.getValue().getValue(),
                                new Timestamp(value.getServerTime().getJavaTime()))
                );
                        System.out.println("Имя ноды: "+nodeId.getIdentifier()+" Сейчас значение ноды:" + value);
                //todo
                System.out.println("Url ноды: "+url);
                sensorDataSender.sendDataToSave(new SensorValueDTO(url,
                        nodeId.getIdentifier().toString(),
                        (Double) value.getValue().getValue(),
                        new Timestamp(value.getServerTime().getJavaTime()))
                );
            });
        }
    }

    public void disconnect(String url) throws Exception {
        if (opcUaClient != null) {
            opcUaClient.get(url).disconnect().get();
            isConnected = false;
            System.out.println("Disconnected from OPC UA server");
        }
    }
}
