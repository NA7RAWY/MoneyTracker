package com.MoneyTracker.MoneyTracker.models.DTOs;

import lombok.Data;

@Data
public class UserLoginDTO {
    
    private String email;

    private String password;
}
