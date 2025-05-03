package com.MoneyTracker.MoneyTracker.repositories;
import com.MoneyTracker.MoneyTracker.models.Transaction;
import com.MoneyTracker.MoneyTracker.models.User;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUser(User user);
    List<Transaction> findByUserAndTransactionDateBetween(User user, LocalDate startDate, LocalDate endDate);

}