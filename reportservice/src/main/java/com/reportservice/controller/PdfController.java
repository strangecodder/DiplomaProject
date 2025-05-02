package com.reportservice.controller;

import com.commondto.dto.ReportDTO;
import com.reportservice.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class PdfController {

    private final ReportService reportService;

    @PostMapping("/convert")
    public ResponseEntity<byte[]> convert(@RequestBody ReportDTO dto) {
        return reportService.convertReportToPdf(dto);
    }

}
