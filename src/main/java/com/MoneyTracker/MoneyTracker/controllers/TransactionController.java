package com.MoneyTracker.MoneyTracker.controllers;
import com.MoneyTracker.MoneyTracker.models.DTOs.SubmitTransactionDTO;
import com.MoneyTracker.MoneyTracker.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public ResponseEntity<SubmitTransactionDTO> createTransaction(@RequestBody SubmitTransactionDTO transactionDTO) {
        SubmitTransactionDTO createdTransaction = transactionService.createTransaction(transactionDTO);
        return ResponseEntity.ok(createdTransaction);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubmitTransactionDTO> getTransactionById(@PathVariable Long id) {
        SubmitTransactionDTO transaction = transactionService.getTransactionById(id);
        return ResponseEntity.ok(transaction);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<SubmitTransactionDTO>> getTransactionsByUserId(@PathVariable Long userId) {
        List<SubmitTransactionDTO> transactions = transactionService.getTransactionsByUserId(userId);
        return ResponseEntity.ok(transactions);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubmitTransactionDTO> updateTransaction(@PathVariable Long id, @RequestBody SubmitTransactionDTO transactionDTO) {
        SubmitTransactionDTO updatedTransaction = transactionService.updateTransaction(id, transactionDTO);
        return ResponseEntity.ok(updatedTransaction);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
        return ResponseEntity.noContent().build();
    }
}