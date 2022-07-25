package com.example.firma.Repositary;

import com.example.firma.Enum.RoleName;
import com.example.firma.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.Email;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RepositaryUsers extends JpaRepository<Users, UUID> {
boolean existsByUsername(@Email String username);
boolean existsByUsernameAndIdNot(@Email String username, UUID id);
Optional<Users> findByUsernameAndCodeEmail(@Email String username, String codeEmail);
Optional<Users> findByUsername(@Email String username);
List<Users> findAllByRole(RoleName role);
Optional<Users> findAllByRoleAndId(RoleName role, UUID id);
}
