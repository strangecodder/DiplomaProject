//package com.example.opcservice.config;
//
//import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.eclipse.milo.opcua.stack.client.*;
//
//import java.net.URI;
//import java.util.concurrent.CompletableFuture;
//
//@Configuration
//public class OpcUAClientConfig {
//
//    private final String endpoint = "opc.tcp://your-opc-server-url:port";
//
//    @Bean
//    public OpcUaClient opcUaClient() throws Exception {
//        OpcUaClient client = OpcUaClient.create(endpoint);
//        client.connect().get();
//        return client;
//    }
//}
