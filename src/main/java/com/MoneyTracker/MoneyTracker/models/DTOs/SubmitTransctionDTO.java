package com.MoneyTracker.MoneyTracker.models.DTOs;
import lombok.Data;

import java.time.LocalDate;

@Data

public class SubmitTransctionDTO {


    private Long userId;

    private LocalDate transactionDate;

    private Double amount;

    private Boolean isIncome;

    private String category;
}
