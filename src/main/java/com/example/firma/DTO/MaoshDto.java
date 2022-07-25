package com.example.firma.DTO;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Data
public class MaoshDto {
    @NotBlank
    private double sum;
}
