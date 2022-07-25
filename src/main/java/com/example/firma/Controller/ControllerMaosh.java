package com.example.firma.Controller;

import com.example.firma.DTO.ApiResponce;
import com.example.firma.DTO.MaoshDto;
import com.example.firma.DTO.VazifaDto;
import com.example.firma.Service.ServiceMaosh;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping(value = "/firma")

public class ControllerMaosh {
    @Autowired
    ServiceMaosh serviceMaosh;

    @PreAuthorize(value = "hasRole('DREKTOR')")
    @PostMapping("/insertMaosh/{id}")
    public ResponseEntity<?> insertMaosh(@PathVariable UUID id,@RequestBody MaoshDto dto){
        ApiResponce apiResponce=serviceMaosh.maoshInsert(id,dto);
        return ResponseEntity.status(apiResponce.isHolat()?201:409).body(apiResponce.getXabar());
    }

    @PreAuthorize(value = "hasRole('DREKTOR')")
    @GetMapping("/selectMaosh")
    public ResponseEntity<?> selectMaoshlar(){
        ApiResponce apiResponce=serviceMaosh.maoshlarSelect();
        return ResponseEntity.status(apiResponce.isHolat()?201:409).body(apiResponce.getXabar()+"\n"+apiResponce.getObject());
    }

    @PreAuthorize(value = "hasRole('DREKTOR')")
    @GetMapping("/selectMaosh/{id}")
    public ResponseEntity<?> selectMaosh(@PathVariable UUID id){
        ApiResponce apiResponce=serviceMaosh.maoshSelect(id);
        return ResponseEntity.status(apiResponce.isHolat()?201:409).body(apiResponce.isHolat()?apiResponce.getXabar()+"\n"+apiResponce.getObject():apiResponce.getXabar());
    }

    @PreAuthorize(value = "hasRole('DREKTOR')")
    @DeleteMapping("/deleteMaosh/{id}")
    public ResponseEntity<?> deleteMaosh(@PathVariable Integer id){
        ApiResponce apiResponce=serviceMaosh.maoshDelete(id);
        return ResponseEntity.status(apiResponce.isHolat()?201:409).body(apiResponce.getXabar());
    }

    @PreAuthorize(value = "hasRole('DREKTOR')")
    @PutMapping("/updateMaosh/{id}")
    public ResponseEntity<?> updateMaosh(@PathVariable Integer id, @Valid @RequestBody MaoshDto dto){
        ApiResponce apiResponce=serviceMaosh.maoshUpdate(id,dto);
        return ResponseEntity.status(apiResponce.isHolat()?201:409).body(apiResponce.getXabar());
    }


}
