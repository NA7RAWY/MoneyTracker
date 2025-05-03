package com.MoneyTracker.MoneyTracker.controllers;
import com.MoneyTracker.MoneyTracker.models.DTOs.SubmitTransactionDTO;
import com.MoneyTracker.MoneyTracker.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/create")
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

    @GetMapping("/user/{userId}/month")
    public ResponseEntity<List<SubmitTransactionDTO>> getTransactionsByUserIdAndMonth(
            @PathVariable Long userId,
            @RequestParam int year,
            @RequestParam int month) {
        List<SubmitTransactionDTO> transactions = transactionService.getTransactionsByUserIdAndMonth(userId, year, month);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/user/{userId}/spending-by-category")
    public ResponseEntity<Map<String, Double>> getMonthlySpendingByCategory(
            @PathVariable Long userId,
            @RequestParam int year,
            @RequestParam int month) {
        Map<String, Double> spendingByCategory = transactionService.getMonthlySpendingByCategory(userId, year, month);
        return ResponseEntity.ok(spendingByCategory);
    }

    @GetMapping("/user/{userId}/income")
    public ResponseEntity<Double> getMonthlyIncome(@PathVariable Long userId,@RequestParam int year, @RequestParam int month) {
        Double income = transactionService.getMonthlyIncome(userId, year, month);
        return ResponseEntity.ok(income);
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