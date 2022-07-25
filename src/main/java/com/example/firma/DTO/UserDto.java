package com.example.firma.DTO;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UserDto {
    @NotNull(message = "FISH Maydonni to'ldir")
    private String fish;
    @NotBlank(message = "bo'sh joy to'ldir")
    private String username;
    @NotBlank(message = "bo'sh joy to'ldir")
    private String password;
}
