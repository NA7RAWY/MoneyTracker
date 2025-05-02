package com.MoneyTracker.MoneyTracker.repositories;
import com.MoneyTracker.MoneyTracker.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}