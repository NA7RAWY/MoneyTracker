package com.MoneyTracker.MoneyTracker.models;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "transactions")
@Data
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

   @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
   private User user;

    @Column(nullable = false)
    private LocalDate transactionDate;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private boolean isIncome;

    @Column(nullable = false)
    private String category;
}