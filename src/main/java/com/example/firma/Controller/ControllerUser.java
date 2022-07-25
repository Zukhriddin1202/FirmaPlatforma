package com.example.firma.Controller;

import com.example.firma.DTO.ApiResponce;
import com.example.firma.DTO.LoginDto;
import com.example.firma.DTO.ParolDto;
import com.example.firma.DTO.UserDto;
import com.example.firma.Model.Users;
import com.example.firma.Service.ServiceUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@Controller
@RequestMapping(value = "/firma")


public class ControllerUser {
    @Autowired
    ServiceUser serviceUser;

    @PreAuthorize(value = "hasRole('DREKTOR')")
    @PostMapping("/menejerAdd")
    public ResponseEntity<?> menejerAdd( @RequestBody UserDto dto){
        ApiResponce apiResponce=serviceUser.menejerAdd(dto);
        return ResponseEntity.status(apiResponce.isHolat()?201:409).body(apiResponce.getXabar());
    }

    @PreAuthorize(value = "hasRole('MANAGER')")
    @PostMapping("/xodimAdd")
    public ResponseEntity<?> xodimAdd(@Valid @RequestBody UserDto dto){
        ApiResponce apiResponce=serviceUser.xodimAdd(dto);
        return ResponseEntity.status(apiResponce.isHolat()?201:409).body(apiResponce.getXabar());
    }


    @GetMapping(value = "/emailniTasdiqlash")
    public ResponseEntity<?> tasdiqlash(@RequestParam String emailCode, @RequestParam String email){
        ApiResponce apiResponce=serviceUser.tasdiqlash(email,emailCode);
        return ResponseEntity.status(apiResponce.isHolat()?201:409).body(apiResponce.getXabar());
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDto dto){
        ApiResponce apiResponce= serviceUser.login(dto);
        return ResponseEntity.status(apiResponce.isHolat()?200:401).body(apiResponce.getXabar()+"\n"+apiResponce.getObject());
    }

    @PreAuthorize(value = "hasAnyRole('MANAGER','XODIM')")
    @PostMapping("/parolniYangilash")
    public  ResponseEntity<?> parol(@Valid @RequestBody ParolDto dto){
        ApiResponce apiResponce= serviceUser.parol(dto);
        return ResponseEntity.status(apiResponce.isHolat()?200:401).body(apiResponce.getXabar());
    }

    @PreAuthorize(value = "hasRole('DREKTOR')")
    @GetMapping("/users")
    public ResponseEntity<?> selectUsers(){
        ApiResponce apiResponce= serviceUser.usersSelect();
        return ResponseEntity.status(apiResponce.isHolat()?200:401).body(apiResponce.getXabar()+"\n"+apiResponce.getObject());
    }

    @PreAuthorize(value = "hasRole('DREKTOR')")
    @GetMapping("/users/{id}")
    public ResponseEntity<?> selectUser(@PathVariable UUID id){
        ApiResponce apiResponce= serviceUser.userSelect(id);
        return ResponseEntity.status(apiResponce.isHolat()?200:401).body(apiResponce.isHolat()?apiResponce.getXabar()+"\n"+apiResponce.getObject():apiResponce.getXabar());
    }

    @PreAuthorize(value = "hasRole('MANAGER')")
    @GetMapping("/usersXodim")
    public ResponseEntity<?> selectUsersXodim(){
        ApiResponce apiResponce= serviceUser.usersSelectXodim();
        return ResponseEntity.status(apiResponce.isHolat()?200:401).body(apiResponce.getXabar()+"\n"+apiResponce.getObject());
    }

    @PreAuthorize(value = "hasRole('MANAGER')")
    @GetMapping("/usersXodim/{id}")
    public ResponseEntity<?> selectUserXodim(@PathVariable UUID id){
        ApiResponce apiResponce= serviceUser.userSelectXodim(id);
        return ResponseEntity.status(apiResponce.isHolat()?200:401).body(apiResponce.isHolat()?apiResponce.getXabar()+"\n"+apiResponce.getObject():apiResponce.getXabar());
    }

    @PreAuthorize(value = "hasRole('DREKTOR')")
    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable UUID id){
        ApiResponce apiResponce= serviceUser.userDelete(id);
        return ResponseEntity.status(apiResponce.isHolat()?200:401).body(apiResponce.getXabar());
    }

    @PreAuthorize(value = "hasRole('MANAGER')")
    @DeleteMapping("/xodim/{id}")
    public ResponseEntity<?> deleteXodim(@PathVariable UUID id){
        ApiResponce apiResponce= serviceUser.xodimDelete(id);
        return ResponseEntity.status(apiResponce.isHolat()?200:401).body(apiResponce.getXabar());
    }

    @PreAuthorize(value = "hasRole('DREKTOR')")
    @PutMapping("/upadteUser/{id}")
    public ResponseEntity<?> updateUser(@PathVariable UUID id,@Valid @RequestBody UserDto dto){
        ApiResponce apiResponce= serviceUser.userUpdate(id,dto);
        return ResponseEntity.status(apiResponce.isHolat()?200:401).body(apiResponce.getXabar());
    }

    @PreAuthorize(value = "hasRole('MANAGER')")
    @PutMapping("/upadteXodim/{id}")
    public ResponseEntity<?> updateXodim(@PathVariable UUID id,@Valid @RequestBody UserDto dto){
        ApiResponce apiResponce= serviceUser.xodimUpdate(id,dto);

        return ResponseEntity.status(apiResponce.isHolat()?200:401).body(apiResponce.getXabar());
    }




}
