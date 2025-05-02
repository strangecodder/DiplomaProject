package com.example.opcservice.controller;

import com.example.opcservice.dto.ConnectionDTO;
import com.example.opcservice.dto.CreateNodeDTO;
import com.example.opcservice.service.OpcUAClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
@CrossOrigin
public class MainController {

    private final OpcUAClientService opcUaClientService;

    @PostMapping("/connect")
    public String connect(@RequestBody ConnectionDTO connectionDTO) {
        try {
            opcUaClientService.connect(connectionDTO);
            return "Connected to OPC UA server.";
        } catch (Exception e) {
            return "Failed to connect: " + e.getMessage();
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
