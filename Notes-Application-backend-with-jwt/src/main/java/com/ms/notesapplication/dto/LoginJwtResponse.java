package com.ms.notesapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginJwtResponse {

    private String username;
    private Set<String> roles;
    private String type;
    private String token;
}
