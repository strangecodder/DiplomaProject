package com.example.opcservice.service;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.milo.opcua.sdk.client.AddressSpace;
import org.eclipse.milo.opcua.sdk.client.api.subscriptions.UaMonitoredItem;
import org.eclipse.milo.opcua.sdk.client.api.subscriptions.UaSubscription;
import org.eclipse.milo.opcua.sdk.client.nodes.UaNode;
import org.eclipse.milo.opcua.sdk.client.nodes.UaVariableNode;
import org.eclipse.milo.opcua.sdk.client.OpcUaClient;

import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.StatusCode;
import org.eclipse.milo.opcua.stack.core.types.builtin.Variant;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.UInteger;
import org.eclipse.milo.opcua.stack.core.types.enumerated.MonitoringMode;
import org.eclipse.milo.opcua.stack.core.types.enumerated.TimestampsToReturn;
import org.eclipse.milo.opcua.stack.core.types.structured.MonitoredItemCreateRequest;
import org.eclipse.milo.opcua.stack.core.types.structured.MonitoringParameters;
import org.eclipse.milo.opcua.stack.core.types.structured.ReadValueId;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Arrays;


@Service
@Slf4j
public class OpcUAClientService {
    private OpcUaClient opcUaClient;
    private boolean isConnected = false;
    private int writeValue = 3;


    public void connect(String endpoint) {
        try {
            opcUaClient = OpcUaClient.create(endpoint);
            opcUaClient.connect().get();
            isConnected = true;
            createMonitoredItem();
        }catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    //@Scheduled(fixedRate = 1000)
    public void readNode() throws Exception {
        if(isConnected) {
            NodeId id = new NodeId(1, "myVariable");

            //AddressSpace space = opcUaClient.getAddressSpace();

//            UaVariableNode variableNode = opcUaClient.getAddressSpace().getVariableNode(id);
//
//            String name = variableNode.getDisplayName().getText();
            //Variant value = variableNode.getValue().getValue();

            Variant value = opcUaClient.readValue(0.0, TimestampsToReturn.Both,id).get().getValue();

            //System.out.println("Name: " + name);
            System.out.println("Значение переменной: " + value);
            //writeNode(id, value);
        }else {
            System.out.println("Ожидаение подключения");
        }
    }

    private void writeNode(NodeId nodeName,Variant value) throws Exception {
        UaVariableNode variableNode = opcUaClient.getAddressSpace().getVariableNode(nodeName);
        //value = variableNode.getValue().getValue();
        Variant variant = new Variant(writeValue++);
        variableNode.writeValue(variant);
    }

    public void readNode(String nodeId) throws Exception {
        NodeId id = new NodeId(1, nodeId);

        AddressSpace space = opcUaClient.getAddressSpace();

        UaVariableNode variableNode = opcUaClient.getAddressSpace().getVariableNode(id);

        String name = variableNode.getDisplayName().getText();
        Variant value = variableNode.getValue().getValue();

        System.out.println("Name: " + name);
        System.out.println("Значение переменной: "+value);

    }

    public void createMonitoredItem() throws Exception {
        UaSubscription subscription = opcUaClient.getSubscriptionManager().createSubscription(1000.0).get();

        NodeId nodeId = new NodeId(1, "myVariable");

        ReadValueId readValueId = new ReadValueId(nodeId,
                UInteger.valueOf(13),
                null,
                null);

        MonitoringParameters parameters = new MonitoringParameters(
                 UInteger.valueOf(1),
                1000.0,
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
                System.out.println("Сейчас значение ноды:" + value);
            });
        }
    }

    public void disconnect() throws Exception {
        if (opcUaClient != null) {
            opcUaClient.disconnect().get();
            isConnected = false;
            System.out.println("Disconnected from OPC UA server");
        }
    }
}
