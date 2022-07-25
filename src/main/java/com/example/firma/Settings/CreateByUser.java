package com.example.firma.Settings;

import com.example.firma.Model.Users;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;
import java.util.UUID;

public class CreateByUser implements AuditorAware<UUID> {
    @Override
    public Optional<UUID> getCurrentAuditor() {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        if(authentication!=null && authentication.isAuthenticated() && !authentication.getPrincipal().equals("anonymousUser")){
            Users user=(Users) authentication.getPrincipal();
            return Optional.of(user.getId());
        }
        return Optional.empty();
    }
}
