package com.MoneyTracker.MoneyTracker.models.DTOs;

import lombok.Data;
import java.util.Map;

@Data
public class SpendingResponse {
    private int userId; 
    private int year;
    private int month;
    private String requestId;
    private Map<String, Double> spendingByCategory;
}