package com.MoneyTracker.MoneyTracker.services;

import com.MoneyTracker.MoneyTracker.models.DTOs.SpendingRequest;
import com.MoneyTracker.MoneyTracker.models.DTOs.SpendingResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SpendingKafkaService {
    private static final Logger log = LoggerFactory.getLogger(SpendingKafkaService.class);

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private KafkaTemplate<String, SpendingResponse> kafkaTemplate;

    @KafkaListener(topics = "spending-requests", groupId = "spending-group")
    public void consumeSpendingRequest(SpendingRequest request) {
        log.info("Received spending request: {}", request);
        try {
            log.debug("Fetching spending for userId={}, year={}, month={}",
                    request.getUserId(), request.getYear(), request.getMonth());
            Map<String, Double> spendingByCategory = transactionService.getMonthlySpendingByCategory(
                    (long) request.getUserId(), request.getYear(), request.getMonth());
            log.debug("Spending by category: {}", spendingByCategory);
            SpendingResponse response = new SpendingResponse();
            response.setUserId(request.getUserId());
            response.setYear(request.getYear());
            response.setMonth(request.getMonth());
            response.setRequestId(request.getRequestId());
            response.setSpendingByCategory(spendingByCategory);
            log.info("Prepared response: {}", response);
            kafkaTemplate.send("spending-responses", request.getRequestId(), response);
            log.info("Sent response to spending-responses: requestId={}", request.getRequestId());
        } catch (Exception e) {
            log.error("Error processing spending request: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to process spending request", e);
        }
    }
}