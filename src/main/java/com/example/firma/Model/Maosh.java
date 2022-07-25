package com.example.firma.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Maosh {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private double sum;

    @OneToOne
    private Users user;

    @CreationTimestamp
    private Timestamp yaratilganVaqat;

    @UpdateTimestamp
    private Timestamp uzgartirilganVaqat;

    public Maosh(double sum, Users user) {
        this.sum = sum;
        this.user = user;
    }
}
