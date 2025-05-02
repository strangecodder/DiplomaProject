package com.example.opcservice.controller;

import com.example.opcservice.dto.ConnectionDTO;
import com.example.opcservice.dto.CreateNodeDTO;
import com.example.opcservice.service.OpcUAClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
@CrossOrigin
public class MainController {

    @Autowired
    private OpcUAClientService opcUaClientService;

    @PostMapping("/connect")
    public String connect(@RequestBody ConnectionDTO connectionDTO) {
        try {
            opcUaClientService.connect(connectionDTO);
            return "Connected to OPC UA server.";
        } catch (Exception e) {
            return "Failed to connect: " + e.getMessage();
        }
    }

    @GetMapping("/read")
    public String readNode(@RequestParam String url,
                           @RequestParam String nodeId) {
        try {
            opcUaClientService.readNode(url,nodeId);
            return "Read node successfully.";
        } catch (Exception e) {
            return "Failed to read node: " + e.getMessage();
        }
    }

    @PostMapping("/disconnect")
    public String disconnect(@RequestParam String url) {
        try {
            opcUaClientService.disconnect(url);
            return "Disconnected from OPC UA server.";
        } catch (Exception e) {
            return "Failed to disconnect: " + e.getMessage();
        }
    }

    @PostMapping("/tryCreate")
    public void tryCreate(@RequestBody CreateNodeDTO createNodeDTO){
        opcUaClientService.createNodeByServerNode(createNodeDTO);
    }

}
