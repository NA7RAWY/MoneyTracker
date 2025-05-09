package com.MoneyTracker.MoneyTracker.models.DTOs;

import lombok.Data;

@Data
public class SpendingRequest {
    private int userId;
    private int year;
    private int month;
    private String requestId;
}