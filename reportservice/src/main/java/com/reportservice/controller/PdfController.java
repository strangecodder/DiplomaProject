package com.reportservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PdfController {

    @PostMapping("/pdfConvert")
    public ResponseEntity<byte[]> pdfConvert(@RequestParam("file") MultipartFile file) {
        return new ResponseEntity<>(new byte[0], HttpStatus.OK);
    }

}
