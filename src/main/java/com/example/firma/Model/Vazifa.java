package com.example.firma.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Vazifa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String vazifaNomi;

    @Column(nullable = false)
    private String izoh;

    @DateTimeFormat
    @Column(nullable = false)
    private LocalDate sana;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    private Users user;

    @CreatedBy
    private UUID creteadBy;

    @LastModifiedBy
    private UUID updateBy;

    public Vazifa(String vazifaNomi, String izoh, LocalDate sana, Users user) {
        this.vazifaNomi = vazifaNomi;
        this.izoh = izoh;
        this.sana = sana;
        this.user = user;
    }
}
