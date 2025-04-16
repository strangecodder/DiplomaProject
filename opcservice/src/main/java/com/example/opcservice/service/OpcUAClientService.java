package com.example.opcservice.service;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.milo.opcua.sdk.client.nodes.UaVariableNode;
import org.eclipse.milo.opcua.sdk.client.OpcUaClient;

import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.Variant;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class OpcUAClientService {
    private OpcUaClient opcUaClient;
    //private UaStackClient opcUaClient;


    public void connect(String endpoint) {
        try {
            opcUaClient = OpcUaClient.create(endpoint);
            opcUaClient.connect().get();
        }catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public void readNode(String nodeId) throws Exception {
        NodeId id = new NodeId(1, nodeId);

        UaVariableNode variableNode = opcUaClient.getAddressSpace().getVariableNode(id);

        String name = variableNode.getDisplayName().getText();
        Variant value = variableNode.getValue().getValue();

        System.out.println("Name: " + name);
        System.out.println("Значение переменной: "+value);

    }

    public void disconnect() throws Exception {
        if (opcUaClient != null) {
            opcUaClient.disconnect().get();
            System.out.println("Disconnected from OPC UA server");
        }
    }
}
