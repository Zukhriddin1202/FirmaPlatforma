package com.example.firma.Token;

import com.example.firma.Enum.RoleName;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class Token {
    long vaqt=36000000;
    Date date=new Date(System.currentTimeMillis()+vaqt);
    String password="Shunday ekan dunyo";
    public String getToken(String username, RoleName role){
        String token= Jwts
                .builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(date)
                .claim("ROLE",role)
                .signWith(SignatureAlgorithm.HS512,password)
                .compact();

        return token;
    }

    public boolean tekToken(String token){
        try{
            Jwts
                    .parser()
                    .setSigningKey(password)
                    .parseClaimsJws(token);
            return true;
        }
        catch (Exception ex){
            ex.getStackTrace();
            return false;
        }
    }

    public String getUsername(String token){
        String subject =Jwts
                .parser()
                .setSigningKey(password)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        return subject;
    }


}
