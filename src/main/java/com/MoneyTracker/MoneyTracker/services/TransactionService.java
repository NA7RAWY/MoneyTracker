package com.MoneyTracker.MoneyTracker.services;
import com.MoneyTracker.MoneyTracker.models.Transaction;
import com.MoneyTracker.MoneyTracker.models.User;
import com.MoneyTracker.MoneyTracker.models.DTOs.SubmitTransactionDTO;
import com.MoneyTracker.MoneyTracker.repositories.TransactionRepository;
import com.MoneyTracker.MoneyTracker.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public SubmitTransactionDTO createTransaction(SubmitTransactionDTO transactionDTO) {
        User user = userRepository.findById(transactionDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User with ID " + transactionDTO.getUserId() + " not found"));

        Transaction transaction = new Transaction();
        transaction.setUser(user);
        transaction.setTransactionDate(transactionDTO.getTransactionDate());
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setIncome(transactionDTO.getIsIncome());
        transaction.setCategory(transactionDTO.getCategory());

        Transaction savedTransaction = transactionRepository.save(transaction);

        return SubmitTransactionDTO.toTransactionDTO(savedTransaction);
    }

    public SubmitTransactionDTO getTransactionById(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Transaction with ID " + id + " not found"));
        return SubmitTransactionDTO.toTransactionDTO(transaction);
    }

    public List<SubmitTransactionDTO> getTransactionsByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with ID " + userId + " not found"));
        return transactionRepository.findByUser(user)
                .stream()
                .map(transaction -> SubmitTransactionDTO.toTransactionDTO(transaction))
                .collect(Collectors.toList());
    }

    @Transactional
    public SubmitTransactionDTO updateTransaction(Long id, SubmitTransactionDTO transactionDTO) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Transaction with ID " + id + " not found"));

        User user = userRepository.findById(transactionDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User with ID " + transactionDTO.getUserId() + " not found"));

        transaction.setUser(user);
        transaction.setTransactionDate(transactionDTO.getTransactionDate());
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setIncome(transactionDTO.getIsIncome());
        transaction.setCategory(transactionDTO.getCategory());

        Transaction updatedTransaction = transactionRepository.save(transaction);

        return SubmitTransactionDTO.toTransactionDTO(updatedTransaction);
    }
    @Transactional
    public void deleteTransaction(Long id) {
        if (!transactionRepository.existsById(id)) {
            throw new IllegalArgumentException("Transaction with ID " + id + " not found");
        }
        transactionRepository.deleteById(id);
    }
}