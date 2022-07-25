package com.example.firma.Filter;

import com.example.firma.Repositary.RepositaryUsers;
import com.example.firma.Service.ServiceUser;
import com.example.firma.Token.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class Filter extends OncePerRequestFilter {
    @Autowired
    Token token;

    @Autowired
    ServiceUser serviceUser;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    String tocen=request.getHeader("TOKEN");
        boolean tekToken = token.tekToken(tocen);
        if (tekToken){
            String username=token.getUsername(tocen);
            UserDetails userDetails = serviceUser.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(request,response);
    }
}
