package com.ms.notesapplication.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class SignupRequest {

    @NotBlank(message = "Username Required!")
    private String username;

    @NotBlank(message = "Password Required!")
    private String password;

    @NotEmpty(message = "Enter Specific Role!")
    private Set<String> roles;
}
