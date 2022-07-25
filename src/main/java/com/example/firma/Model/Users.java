package com.example.firma.Model;

import com.example.firma.Enum.RoleName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.sql.Timestamp;
import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Users implements UserDetails {
    @Id
    @GeneratedValue
    private UUID id;
    @Column(nullable = false)
    private String fish;
    @Email
    @Column(nullable = false,unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @Enumerated(EnumType.STRING)
    private RoleName role;

    @CreationTimestamp
    @Column(nullable = false,updatable = false)
    private Timestamp yaratilganVaqt;

    @UpdateTimestamp
    @Column(nullable = false)
    private Timestamp yangilanganVaqt;

    private String codeEmail;

    private boolean accountNonExpired=true;

    private boolean accountNonLocked=true;

    private boolean credentialsNonExpired=true;

    private boolean enabled=false;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.createAuthorityList(role.name());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public Users(String fish, String username, String password, RoleName role, boolean enabled) {
        this.fish = fish;
        this.username = username;
        this.password = password;
        this.role = role;
        this.enabled = enabled;
    }
}
