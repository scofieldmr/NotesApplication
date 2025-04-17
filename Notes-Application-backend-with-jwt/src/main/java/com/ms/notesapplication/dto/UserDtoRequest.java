package com.ms.notesapplication.dto;

import com.ms.notesapplication.entity.Roles;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDtoRequest {

    @NotNull(message = "Id Required.")
    private Long id;
    @NotBlank(message = "Username Required.")
    private String username;
    @NotEmpty(message = "Roles Required.")
    private Set<String> roles = new HashSet<>();
}
