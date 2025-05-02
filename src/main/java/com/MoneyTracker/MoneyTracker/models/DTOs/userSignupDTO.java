package com.MoneyTracker.MoneyTracker.models.DTOs;
import lombok.Data;
@Data
public class userSignupDTO {
    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String jobType;
}
