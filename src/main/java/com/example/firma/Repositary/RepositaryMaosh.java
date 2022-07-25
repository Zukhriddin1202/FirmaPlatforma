package com.example.firma.Repositary;

import com.example.firma.Model.Maosh;
import com.example.firma.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RepositaryMaosh extends JpaRepository<Maosh,Integer> {
    List<Maosh> findAllByUser(Users user);
}
