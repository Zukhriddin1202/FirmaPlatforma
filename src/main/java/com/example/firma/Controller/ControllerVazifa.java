package com.example.firma.Controller;

import com.example.firma.DTO.ApiResponce;
import com.example.firma.DTO.UserDto;
import com.example.firma.DTO.VazifaDto;
import com.example.firma.Service.ServiceVazifa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/firma")

public class ControllerVazifa {
    @Autowired
    ServiceVazifa serviceVazifa;

    @PreAuthorize(value = "hasRole('DREKTOR')")
    @PostMapping("/insertVazifa")
    public ResponseEntity<?> insertVazifa(@RequestBody VazifaDto dto){
        ApiResponce apiResponce=serviceVazifa.vazifaInsert(dto);
        return ResponseEntity.status(apiResponce.isHolat()?201:409).body(apiResponce.getXabar());
    }

    @PreAuthorize(value = "hasRole('MANAGER')")
    @PostMapping("/insertVazifaXodim")
    public ResponseEntity<?> insertVazifaXodim(@RequestBody VazifaDto dto){
        ApiResponce apiResponce=serviceVazifa.vazifaInsertXodim(dto);
        return ResponseEntity.status(apiResponce.isHolat()?201:409).body(apiResponce.getXabar());
    }

    @PreAuthorize(value = "hasRole('DREKTOR')")
    @GetMapping("/selectVazifalar")
    public ResponseEntity<?> selectVazifalar(){
        ApiResponce apiResponce=serviceVazifa.vazifalarSelect();
        return ResponseEntity.status(apiResponce.isHolat()?201:409).body(apiResponce.getXabar()+"\n"+apiResponce.getObject());
    }

    @PreAuthorize(value = "hasRole('MANAGER')")
    @GetMapping("/selectVazifalarXodim")
    public ResponseEntity<?> selectVazifalarXodim(){
        ApiResponce apiResponce=serviceVazifa.vazifalarSelectXodim();
        return ResponseEntity.status(apiResponce.isHolat()?201:409).body(apiResponce.getXabar()+"\n"+apiResponce.getObject());
    }

    @PreAuthorize(value = "hasRole('DREKTOR')")
    @GetMapping("/selectVazifa/{id}")
    public ResponseEntity<?> selectVazifa(@PathVariable Integer id){
        ApiResponce apiResponce=serviceVazifa.vazifaSelect(id);
        return ResponseEntity.status(apiResponce.isHolat()?201:409).body(apiResponce.isHolat()?apiResponce.getXabar()+"\n"+apiResponce.getObject():apiResponce.getXabar());
    }

    @PreAuthorize(value = "hasRole('MANAGER')")
    @GetMapping("/selectVazifalXodim/{id}")
    public ResponseEntity<?> selectVazifaXodim(@PathVariable Integer id){
        ApiResponce apiResponce=serviceVazifa.vazifaSelectXodim(id);
        return ResponseEntity.status(apiResponce.isHolat()?201:409).body(apiResponce.isHolat()?apiResponce.getXabar()+"\n"+apiResponce.getObject():apiResponce.getXabar());
    }

    @PreAuthorize(value = "hasRole('DREKTOR')")
    @DeleteMapping("/deleteVazifa/{id}")
    public ResponseEntity<?> deleteVazifa(@PathVariable Integer id){
        ApiResponce apiResponce=serviceVazifa.vazifaDelete(id);
        return ResponseEntity.status(apiResponce.isHolat()?201:409).body(apiResponce.getXabar());
    }

    @PreAuthorize(value = "hasRole('MANAGER')")
    @DeleteMapping("/deleteVazifaXodim/{id}")
    public ResponseEntity<?> deleteVazifaXodim(@PathVariable Integer id){
        ApiResponce apiResponce=serviceVazifa.vazifaDeleteXodim(id);
        return ResponseEntity.status(apiResponce.isHolat()?201:409).body(apiResponce.getXabar());
    }

    @PreAuthorize(value = "hasRole('DREKTOR')")
    @PutMapping("/updateVazifa/{id}")
    public ResponseEntity<?> updateVazifa(@PathVariable Integer id, @Valid @RequestBody VazifaDto dto){
        ApiResponce apiResponce=serviceVazifa.vazifaUpdate(id,dto);
        return ResponseEntity.status(apiResponce.isHolat()?201:409).body(apiResponce.getXabar());
    }

    @PreAuthorize(value = "hasRole('MANAGER')")
    @PutMapping("/updateVazifaXodim/{id}")
    public ResponseEntity<?> updateVazifaXodim(@PathVariable Integer id, @Valid @RequestBody VazifaDto dto){
        ApiResponce apiResponce=serviceVazifa.vazifaUpdateXodim(id,dto);
        return ResponseEntity.status(apiResponce.isHolat()?201:409).body(apiResponce.getXabar());
    }


    @GetMapping("/vazifa")
    public ResponseEntity<?> vazifa(){
        ApiResponce apiResponce=serviceVazifa.vazifa();
        return ResponseEntity.status(201).body(apiResponce.isHolat()?apiResponce.getXabar()+"\n"+apiResponce.getObject():apiResponce.getXabar());
    }

}
