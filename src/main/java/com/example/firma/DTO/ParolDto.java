package com.example.firma.DTO;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ParolDto {
    @NotBlank(message = "Username yozing")
    private String username;
    @NotBlank(message = "Parol yozing")
    private String parol;
    @NotBlank(message = "parol qayta yozing")
    private String reparol;
}
