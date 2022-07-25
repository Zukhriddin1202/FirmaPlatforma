package com.example.firma.Service;

import com.example.firma.DTO.ApiResponce;
import com.example.firma.DTO.MaoshDto;
import com.example.firma.Model.Maosh;
import com.example.firma.Model.Users;
import com.example.firma.Repositary.RepositaryMaosh;
import com.example.firma.Repositary.RepositaryUsers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ServiceMaosh {
    @Autowired
    RepositaryMaosh repositaryMaosh;

    @Autowired
    RepositaryUsers repositaryUsers;


    public ApiResponce maoshInsert(UUID id, MaoshDto dto) {
        Optional<Users> byId = repositaryUsers.findById(id);
        if(byId.isPresent()){
            Maosh maosh=new Maosh(dto.getSum(),byId.get());
            repositaryMaosh.save(maosh);
            return new ApiResponce("Maosh belgilandi",true);
        }
        return new ApiResponce("Bunday ishchi yo'q",false);
    }

    public ApiResponce maoshlarSelect() {
        List<Maosh> maoshes=repositaryMaosh.findAll();
        return new ApiResponce("Maoshlar ro'yxati",true,maoshes);
    }

    public ApiResponce maoshSelect(UUID id) {
        Optional<Users> byId = repositaryUsers.findById(id);
        if (byId.isPresent()) {
            List<Maosh> optionalMaosh = repositaryMaosh.findAllByUser(byId.get());
            if (!optionalMaosh.isEmpty()) {
                return new ApiResponce("Maoshlar ro'yxati", true, optionalMaosh);
            }
            return new ApiResponce("Bu ishchiga maosh berilmagan", false);
        }
        return new ApiResponce("Bu ishchi yo'q", false);
    }

    public ApiResponce maoshDelete(Integer id) {
        Optional<Maosh> optionalMaosh=repositaryMaosh.findById(id);
        if(optionalMaosh.isPresent())
        {
            repositaryMaosh.deleteById(id);
            return new ApiResponce("O'chirildi", true);
        }
        return new ApiResponce("Bunday maosh ajratilmagan", false);
    }

    public ApiResponce maoshUpdate(Integer id,MaoshDto dto) {
        Optional<Maosh> optionalMaosh=repositaryMaosh.findById(id);
        if(optionalMaosh.isPresent())
        {
            Maosh maosh=optionalMaosh.get();
            maosh.setSum(dto.getSum());
            repositaryMaosh.save(maosh);
            return new ApiResponce("O'zgartirildi", true);
        }
        return new ApiResponce("Bunday maosh ajratilmagan", false);
    }
}
