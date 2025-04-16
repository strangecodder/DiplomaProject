package com.example.opcservice.controller;

import com.example.opcservice.service.OpcUAClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class MainController {

    @Autowired
    private OpcUAClientService opcUaClientService;

    @PostMapping("/connect")
    public String connect(@RequestParam String url) {
        try {
            opcUaClientService.connect(url);
            return "Connected to OPC UA server.";
        } catch (Exception e) {
            return "Failed to connect: " + e.getMessage();
        }
    }

    @GetMapping("/read")
    public String readNode(@RequestParam String nodeId) {
        try {
            opcUaClientService.readNode(nodeId);
            return "Read node successfully.";
        } catch (Exception e) {
            return "Failed to read node: " + e.getMessage();
        }
    }

    @PostMapping("/disconnect")
    public String disconnect() {
        try {
            opcUaClientService.disconnect();
            return "Disconnected from OPC UA server.";
        } catch (Exception e) {
            return "Failed to disconnect: " + e.getMessage();
        }
    }
}
