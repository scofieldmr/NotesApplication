package com.ms.notesapplication.dto;

import com.ms.notesapplication.entity.Roles;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class UserDtoResponse {
    private String username;
    private Set<String> roles = new HashSet<>();
}
