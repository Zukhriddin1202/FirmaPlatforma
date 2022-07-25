package com.example.firma.Service;

import com.example.firma.DTO.ApiResponce;
import com.example.firma.DTO.LoginDto;
import com.example.firma.DTO.ParolDto;
import com.example.firma.DTO.UserDto;
import com.example.firma.Enum.RoleName;
import com.example.firma.Model.Users;
import com.example.firma.Repositary.RepositaryUsers;
import com.example.firma.Token.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ServiceUser implements UserDetailsService {
    @Autowired
    RepositaryUsers repositaryUsers;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    Token token;

    public ApiResponce menejerAdd(UserDto dto){
        boolean b = repositaryUsers.existsByUsername(dto.getUsername());
        if(b)
            return new ApiResponce("Bunday menejer allaqachon mavjud",false);
        Users u=new Users();
        u.setFish(dto.getFish());
        u.setUsername(dto.getUsername());
        u.setPassword(passwordEncoder.encode(dto.getPassword()));
        u.setRole(RoleName.ROLE_MANAGER);
        u.setCodeEmail(UUID.randomUUID().toString());
        u.setEnabled(true);
        repositaryUsers.save(u);
        boolean messegeEmail=sendEmail(u.getUsername(),u.getCodeEmail());
        return new ApiResponce("Ro'yxatdan o'tdi",true);
    }
    private boolean sendEmail(String email,String code){
       try {
           SimpleMailMessage mailMessage=new SimpleMailMessage();
           mailMessage.setFrom("test@gmail.com");
           mailMessage.setTo(email);
           mailMessage.setSubject("Emailni tasdiqlang");
           mailMessage.setText("<a href='http://localhost:8080/firma/emailniTasdiqlash?emailCode="+code+"&email="+email+"'>Profilni faolashtiring</a>");
           javaMailSender.send(mailMessage);
           return true;
       }
       catch (Exception ex){
           ex.getStackTrace();
           return false;
       }
    }

    public ApiResponce tasdiqlash( String email, String code){
        Optional<Users> byEmailAndCodeEmail =repositaryUsers.findByUsernameAndCodeEmail(email,code);
        if(byEmailAndCodeEmail.isPresent()){
            Users users=byEmailAndCodeEmail.get();
            users.setEnabled(true);
            users.setCodeEmail(null);
            repositaryUsers.save(users);
            return new ApiResponce("Hisobingiz muavfaqiyatli faolashtirildi",true);
        }
        return new ApiResponce("Hisobingiz allaqchon faolashtirilgan",false);
    }

    public ApiResponce login(LoginDto dto) {
    try {
        Authentication authenticate=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(),dto.getPassword()));
        Users users= (Users) authenticate.getPrincipal();
        String tocen=token.getToken(users.getUsername(),users.getRole());
        return new ApiResponce("Xush kelibsiz", true, tocen);
    }
    catch (BadCredentialsException ex){
        ex.getStackTrace();
        return new ApiResponce("Parol yoki login xato", false);
    }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repositaryUsers.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("Bunday foydalanuvchi topilmadi"));
    }

    public ApiResponce xodimAdd(UserDto dto) {
        if(repositaryUsers.existsByUsername(dto.getUsername()))
            return new ApiResponce("Bunday foydalanvchi allaqachon mavjud",false);
        Users u=new Users();
        u.setFish(dto.getFish());
        u.setUsername(dto.getUsername());
        u.setPassword(passwordEncoder.encode(dto.getPassword()));
        u.setRole(RoleName.ROLE_XODIM);
        u.setEnabled(true);
        u.setCodeEmail(UUID.randomUUID().toString());
        repositaryUsers.save(u);
        boolean messegeEmail=sendEmail(u.getUsername(),u.getCodeEmail());
        return new ApiResponce("Ro'yxatdan o'tdi",true);
    }

    public ApiResponce parol(ParolDto dto) {
        Optional<Users> byUsername = repositaryUsers.findByUsername(dto.getUsername());
        if(byUsername.isPresent()){
            if(dto.getParol().equals(dto.getReparol())){
                Users users=byUsername.get();
                users.setPassword(passwordEncoder.encode(dto.getParol()));
                repositaryUsers.save(users);
                return new ApiResponce("Parol saqalandi",true);
            }
            return new ApiResponce("Parollar mos kelmadi",false);
        }
        return new ApiResponce("Bunday foydalanuvchi yo'q",false);
    }

    public ApiResponce usersSelect() {
        List<Users> f=repositaryUsers.findAll();
        return new ApiResponce("Firmadagi ishchilar ro'yxati",true,f);
    }

    public ApiResponce userSelect(UUID id) {
        Optional<Users> byId = repositaryUsers.findById(id);
        if(byId.isPresent()){
            Users users=byId.get();
            return new ApiResponce("Ma'lumot topildi",true,users);
        }
        return new ApiResponce("Bunday ishchi topilmadi",false);
    }

    public ApiResponce usersSelectXodim() {
        List<Users> f=repositaryUsers.findAllByRole(RoleName.ROLE_XODIM);
        return new ApiResponce("Firmadagi xodimlar ro'yxati",true,f);
    }

    public ApiResponce userSelectXodim(UUID id) {
        Optional<Users> byId = repositaryUsers.findAllByRoleAndId(RoleName.ROLE_XODIM,id);
        if(byId.isPresent()){
            Users users=byId.get();
            return new ApiResponce("Ma'lumot topildi",true,users);
        }
        return new ApiResponce("Bunday ishchi topilmadi",false);
    }

    public ApiResponce userDelete(UUID id) {
        Optional<Users> byId = repositaryUsers.findById(id);
        if(byId.isPresent()){
            repositaryUsers.deleteById(id);
            return new ApiResponce("O'chirildi",true);
        }
        return new ApiResponce("Bunday ishchi topilmadi",false);
    }

    public ApiResponce xodimDelete(UUID id) {
        Optional<Users> byId = repositaryUsers.findAllByRoleAndId(RoleName.ROLE_XODIM,id);
        if(byId.isPresent()){
            repositaryUsers.deleteById(id);
            return new ApiResponce("O'chirildi",true);
        }
        return new ApiResponce("Bunday xodim topilmadi",false);
    }

    public ApiResponce userUpdate(UUID id, UserDto dto) {
        Optional<Users> byUser=repositaryUsers.findById(id);
        if(byUser.isPresent()){
            if(repositaryUsers.existsByUsernameAndIdNot(dto.getUsername(),id))
                return new ApiResponce("Bunday foydalanvchi allaqachon mavjud",false);
            Users u=byUser.get();
            u.setFish(dto.getFish());
            u.setUsername(dto.getUsername());
            u.setPassword(passwordEncoder.encode(dto.getPassword()));
            repositaryUsers.save(u);
            return new ApiResponce("Yangilandi",true);
        }
        return new ApiResponce("Bunday ishchi yo'q",false);
    }

    public ApiResponce xodimUpdate(UUID id, UserDto dto) {
        Optional<Users> byUser=repositaryUsers.findAllByRoleAndId(RoleName.ROLE_XODIM,id);
        if(byUser.isPresent()){
            if(repositaryUsers.existsByUsernameAndIdNot(dto.getUsername(),id))
                return new ApiResponce("Bunday foydalanvchi allaqachon mavjud",false);
            Users u=byUser.get();
            u.setFish(dto.getFish());
            u.setUsername(dto.getUsername());
            repositaryUsers.save(u);
            return new ApiResponce("Yangilandi",true);
        }
        return new ApiResponce("Bunday xodim yo'q",false);
    }


}
