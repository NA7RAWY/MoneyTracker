package com.MoneyTracker.MoneyTracker.models.DTOs;
import lombok.Data;

import java.time.LocalDate;

import com.MoneyTracker.MoneyTracker.models.Transaction;

@Data

public class SubmitTransctionDTO {


    private Long userId;

    private LocalDate transactionDate;

    private Double amount;

    private Boolean isIncome;

    private String category;

    public static SubmitTransctionDTO toTransctionDTO(Transaction transaction) {
        SubmitTransctionDTO transactionDTO = new SubmitTransctionDTO();
        transactionDTO.setUserId(transaction.getUser().getId());
        transactionDTO.setTransactionDate(transaction.getTransactionDate());
        transactionDTO.setAmount(transaction.getAmount());
        transactionDTO.setIsIncome(transaction.isIncome());
        transactionDTO.setCategory(transaction.getCategory());
        return transactionDTO;
}

    }    

