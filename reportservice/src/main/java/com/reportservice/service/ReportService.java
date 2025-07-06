package com.reportservice.service;

import com.commondto.dto.FullReportDTO;
import com.commondto.dto.ReportDTO;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.reportservice.kafka.listener.KafkaFullReportListener;
import com.reportservice.kafka.producer.KafkaReportSender;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final KafkaFullReportListener fullReportListener;
    private final KafkaReportSender reportSender;

    private Long generateCode(){
        return (long)(Math.random()*900000)+1;
    }

    public ResponseEntity<byte[]> convertReportToPdf(ReportDTO reportDTO) {

        Long code = Stream.generate(this::generateCode).
                filter(c -> !fullReportListener.isKeyExists(c)).
                findFirst().
                orElse(null);

        reportDTO.setCode(code);
        reportSender.sendReport(reportDTO);

        FullReportDTO fullReportDTO = fullReportListener.getFullReport(code,10000);

        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            Document document = new Document();

            String fontUrl = "/usr/share/fonts/liberation/LiberationSerif-Italic.ttf";

            BaseFont baseFont = BaseFont.createFont(fontUrl, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

            Font fontTitle = new Font(baseFont, 18, Font.BOLD);
            Font fontTitleParagraph = new Font(baseFont, 16, Font.BOLD);
            Font fontNormal1 = new Font(baseFont, 16);
            Font fontNormal = new Font(baseFont, 14);

            PdfWriter.getInstance(document, byteArrayOutputStream);
            document.open();

            Paragraph title = new Paragraph("Отчёт о функционировании",
                    fontTitle);

            document.add(title);

            Paragraph name = new Paragraph("Отчёт подготовил "+ fullReportDTO.getUsername(),fontNormal1);
            document.add(name);

            Paragraph title1 = new Paragraph("Информация об оборудовании",
                    fontTitleParagraph);

            document.add(title1);

            Paragraph machineName = new Paragraph("Название оборудования: "+ fullReportDTO.getMachineName(),fontNormal);
            Paragraph machineType = new Paragraph("Тип оборудования: "+ fullReportDTO.getMachineType(),fontNormal);
            document.add(machineName);
            document.add(machineType);

            Paragraph sensorName = new Paragraph("Название датчика: "+ fullReportDTO.getSensorName(),fontNormal);
            Paragraph sensorType = new Paragraph("Тип датчика: "+ fullReportDTO.getSensorType(),fontNormal);
            Paragraph opcServer = new Paragraph("Opc Ua сервер датчика: "+ fullReportDTO.getOpcServer(),fontNormal);
            Paragraph nodeName = new Paragraph("Нода, характерезующая датчик: "+ fullReportDTO.getNodeName(),fontNormal);

            document.add(sensorName);
            document.add(sensorType);
            document.add(opcServer);
            document.add(nodeName);

            Paragraph title2 = new Paragraph("Описание функционирования оборудования",
                    fontTitleParagraph);
            Paragraph description = new Paragraph(fullReportDTO.getReportDescription(),fontNormal);

            document.add(title2);
            document.add(description);

            Paragraph titleGraphic = new Paragraph("Визуализация функционирования",
                    fontTitleParagraph);

            String base64Image = fullReportDTO.getImageData().split(",")[1];
            byte[] imageBytes = java.util.Base64.getDecoder().decode(base64Image);

            Image pdfImage = Image.getInstance(imageBytes);
            pdfImage.scalePercent(50);
            document.add(titleGraphic);
            document.add(pdfImage);

            Paragraph date = new Paragraph("Время отчёта составления отчёта"+ fullReportDTO.getReportDate(),fontNormal1);

            document.add(date);
            document.close();

            byte[] pdfBytes = byteArrayOutputStream.toByteArray();

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=Functionality.pdf");

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

