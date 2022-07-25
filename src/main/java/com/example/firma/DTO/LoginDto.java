package com.example.firma.DTO;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class LoginDto {
    @NotBlank(message = "username yozing")
    private String username;
    @NotBlank(message = "password yozing")
    private String password;
}
