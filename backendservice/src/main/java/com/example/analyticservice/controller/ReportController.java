package com.example.analyticservice.controller;

import com.commondto.dto.FullReportDTO;
import com.commondto.dto.ReportDTO;
import com.example.analyticservice.service.PdfService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pdf")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class ReportController {

    private final PdfService pdfService;

    @PostMapping("/convert")
    public ResponseEntity<byte[]> convert(@RequestBody ReportDTO dto) {
        return pdfService.convertReportToPdf(dto);
    }
}
