package com.example.firma.Service;

import com.example.firma.DTO.ApiResponce;
import com.example.firma.DTO.VazifaDto;
import com.example.firma.Enum.RoleName;
import com.example.firma.Model.Users;
import com.example.firma.Model.Vazifa;
import com.example.firma.Repositary.RepositaryUsers;
import com.example.firma.Repositary.RepositaryVazifa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceVazifa {
    @Autowired
    RepositaryVazifa repositaryVazifa;

    @Autowired
    RepositaryUsers repositaryUsers;

    @Autowired
    JavaMailSender javaMailSender;

    private void sendEmail(String email){
        try {
            SimpleMailMessage mailMessage=new SimpleMailMessage();
            mailMessage.setFrom("test@gmail.com");
            mailMessage.setTo(email);
            mailMessage.setSubject("Vazivani berildi");
            mailMessage.setText("Vazifani vaqtida bajaring");
            javaMailSender.send(mailMessage);
        }
        catch (Exception ex){
            ex.getStackTrace();
        }
    }

    public ApiResponce vazifaInsert(VazifaDto dto) {
        Optional<Users> byId = repositaryUsers.findById(dto.getUserId());
        if(byId.isPresent()){
            Vazifa vazifa=new Vazifa(dto.getNomi(), dto.getIzoh(), dto.getSana(), byId.get());
            repositaryVazifa.save(vazifa);
            sendEmail(byId.get().getUsername());
            return new ApiResponce("Vazifa joylandi",true);
        }
        return new ApiResponce("Bunday ishchi yo'q",false);
    }

    public ApiResponce vazifaInsertXodim(VazifaDto dto) {
        Optional<Users> byId = repositaryUsers.findAllByRoleAndId(RoleName.ROLE_XODIM,dto.getUserId());
        if(byId.isPresent()){
            Vazifa vazifa=new Vazifa(dto.getNomi(), dto.getIzoh(), dto.getSana(), byId.get());
            repositaryVazifa.save(vazifa);
            sendEmail(byId.get().getUsername());
            return new ApiResponce("Vazifa joylandi",true);
        }
        return new ApiResponce("Bunday xodim yo'q",false);
    }

    public ApiResponce vazifalarSelect() {
        List<Vazifa> f=repositaryVazifa.findAll();
        return new ApiResponce("Barcha vazifalar ro'yxati",true,f);
    }


    public ApiResponce vazifalarSelectXodim() {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        Users user=(Users) authentication.getPrincipal();
        List<Vazifa> f=repositaryVazifa.findAllByCreteadBy(user.getId());
        return new ApiResponce("Barcha vazifalar ro'yxati",true,f);
    }

    public ApiResponce vazifaSelect(Integer id) {
       Optional<Vazifa> optionalVazifa=repositaryVazifa.findById(id);
       if(optionalVazifa.isPresent())
         return new ApiResponce("Vazifa topildi",true,optionalVazifa.get());
        return new ApiResponce("Bunday vazifa yo'q",false);
    }

    public ApiResponce vazifaSelectXodim(Integer id) {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        Users user=(Users) authentication.getPrincipal();
        Optional<Vazifa> optionalVazifa=repositaryVazifa.findAllByCreteadByAndId(user.getId(),id);
        if(optionalVazifa.isPresent())
            return new ApiResponce("Vazifa topildi",true,optionalVazifa.get());
        return new ApiResponce("Bunday vazifa yo'q",false);
    }

    public ApiResponce vazifaDelete(Integer id) {
        Optional<Vazifa> optionalVazifa=repositaryVazifa.findById(id);
        if(optionalVazifa.isPresent()) {
            repositaryVazifa.deleteById(id);
            return new ApiResponce("O'chirildi", true);
        }
        return new ApiResponce("Bunday vazifa yo'q",false);
    }

    public ApiResponce vazifaDeleteXodim(Integer id) {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        Users user=(Users) authentication.getPrincipal();
        Optional<Vazifa> optionalVazifa=repositaryVazifa.findAllByCreteadByAndId(user.getId(),id);
        if(optionalVazifa.isPresent()) {
            repositaryVazifa.deleteById(id);
            return new ApiResponce("O'chirildi", true);
        }
        return new ApiResponce("Bunday vazifa yo'q",false);
    }

    public ApiResponce vazifaUpdate(Integer id, VazifaDto dto) {
        Optional<Vazifa> optionalVazifa=repositaryVazifa.findById(id);
        if(optionalVazifa.isPresent()) {
            Optional<Users> byId = repositaryUsers.findById(dto.getUserId());
            if(byId.isPresent()) {
                Vazifa vazifa = optionalVazifa.get();
                vazifa.setVazifaNomi(dto.getNomi());
                vazifa.setIzoh(dto.getIzoh());
                vazifa.setSana(dto.getSana());
                vazifa.setUser(byId.get());
                repositaryVazifa.save(vazifa);

                sendEmail(byId.get().getUsername());
                return new ApiResponce("O'zgartirildi", true);
            }
            return new ApiResponce("Bunday ishchi yo'q", false);
        }
        return new ApiResponce("Bunday vazifa yo'q",false);
    }

    public ApiResponce vazifaUpdateXodim(Integer id, VazifaDto dto) {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        Users user=(Users) authentication.getPrincipal();
        Optional<Vazifa> optionalVazifa=repositaryVazifa.findAllByCreteadByAndId(user.getId(),id);
        if(optionalVazifa.isPresent()) {
            Optional<Users> byId = repositaryUsers.findAllByRoleAndId(RoleName.ROLE_XODIM,dto.getUserId());
            if(byId.isPresent()) {
                Vazifa vazifa = optionalVazifa.get();
                vazifa.setVazifaNomi(dto.getNomi());
                vazifa.setIzoh(dto.getIzoh());
                vazifa.setSana(dto.getSana());
                vazifa.setUser(byId.get());
                repositaryVazifa.save(vazifa);
                sendEmail(byId.get().getUsername());
                return new ApiResponce("O'zgartirildi", true);
            }
            return new ApiResponce("Bunday xodim yo'q", false);
        }
        return new ApiResponce("Bunday xodim yo'q",false);
    }

    public ApiResponce vazifa() {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        Users user=(Users) authentication.getPrincipal();
        Optional<Vazifa> optionalVazifa=repositaryVazifa.findAllByUser(user);
        if(optionalVazifa.isPresent()){
            return new ApiResponce("Sizga berilgan vazifalar",true,optionalVazifa.get());
        }
        return new ApiResponce("Sizga vazifa berilmagan",false);
    }
}
