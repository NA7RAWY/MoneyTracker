package com.MoneyTracker.MoneyTracker.models.DTOs;

import com.MoneyTracker.MoneyTracker.models.Transaction;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SubmitTransactionDTO {

    private Long id;

    private Long userId;

    private LocalDate transactionDate;

    private Double amount;

    private Boolean isIncome;

    
    private String category;

    public static SubmitTransactionDTO toTransactionDTO(Transaction transaction) {
        SubmitTransactionDTO transactionDTO = new SubmitTransactionDTO();
        transactionDTO.setId(transaction.getId());
        transactionDTO.setUserId(transaction.getUser().getId());
        transactionDTO.setTransactionDate(transaction.getTransactionDate());
        transactionDTO.setAmount(transaction.getAmount());
        transactionDTO.setIsIncome(transaction.isIncome());
        transactionDTO.setCategory(transaction.getCategory());
        return transactionDTO;
    }
}