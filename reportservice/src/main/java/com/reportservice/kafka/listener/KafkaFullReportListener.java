package com.reportservice.kafka.listener;

import com.commondto.dto.FullReportDTO;
import com.commondto.dto.ReportDTO;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.concurrent.*;

@Component
public class KafkaFullReportListener {

    private ConcurrentHashMap<Long, CompletableFuture<FullReportDTO>> futures = new ConcurrentHashMap<>();

    public boolean isKeyExists(Long key){
        return futures.containsKey(key);
    }

    public FullReportDTO getFullReport(Long code,long timeout) {
        CompletableFuture<FullReportDTO> future = new CompletableFuture<>();
        futures.put(code, future);
        try {
            return future.get(timeout, TimeUnit.MILLISECONDS);
        }catch (TimeoutException | ExecutionException | InterruptedException e){
            return null;
        }finally {
            futures.remove(code);
        }
    }

    @KafkaListener(topics = "full-report",groupId = "report_group",containerFactory = "kafkaListenerContainerFactory")
    public void listenFullReport(FullReportDTO fullReportDTO){
        Long code = fullReportDTO.getCode();
        CompletableFuture<FullReportDTO> future = futures.get(code);
        if(future != null){
            future.complete(fullReportDTO);
        }
    }
}
