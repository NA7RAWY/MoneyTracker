package com.MoneyTracker.MoneyTracker.repositories;

import com.MoneyTracker.MoneyTracker.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}