package com.example.firma.Conpanent;

import com.example.firma.Enum.RoleName;
import com.example.firma.Model.Users;
import com.example.firma.Repositary.RepositaryUsers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component

public class DataLoader implements ApplicationRunner {

    @Autowired
    RepositaryUsers repositaryUsers;


    @Value("${spring.sql.init.mode}")
    private String initMode;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if(initMode.equals("always")){
            Users user1=new Users("Zukhriddin","test2@gmail.com",passwordEncoder.encode("12345"),RoleName.ROLE_DREKTOR,true);
            Users user2=new Users("Zukhriddin Botirov","test5@gmail.com",passwordEncoder.encode("12345"),RoleName.ROLE_XODIM,true);
            repositaryUsers.save(user1);
            repositaryUsers.save(user2);
        }
    }
}
