package com.example.firma.DTO;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class VazifaDto {
    @NotNull(message = "Nomini yozing")
    private String nomi;
    @NotNull(message = "Izohini yozing")
    private String izoh;
    @DateTimeFormat
    private LocalDate sana;
    @NotBlank
    private UUID userId;
}
