package com.example.firma.Repositary;

import com.example.firma.Model.Users;
import com.example.firma.Model.Vazifa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RepositaryVazifa extends JpaRepository<Vazifa, Integer> {
    Optional<Vazifa> findAllByCreteadByAndId(UUID creteadBy, Integer id);
    List<Vazifa> findAllByCreteadBy(UUID creteadBy);
    Optional<Vazifa> findAllByUser(Users user);
}
