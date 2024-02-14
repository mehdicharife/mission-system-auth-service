package io.github.mehdicharife.missionauthservice.config.security.filters;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.github.mehdicharife.missionauthservice.service.JwtService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class JwtBearerFilter extends OncePerRequestFilter  {

    private JwtService jwtService;

    private UserDetailsService userDetailsService;

    public JwtBearerFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }


    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        if(authorizationHeader == null || !authorizationHeader.startsWith("Bearer")) {
            filterChain.doFilter(request, response);
            return;
        }

        /*
        TODOs: 
            - Use the jwtService to extract the authorities from the token instead of getting the 
              user from the database and then getting the authorities;
            - 
        */
        try {
            String username = this.jwtService.extractUsername(authorizationHeader.substring("Bearer".length()));
            UserDetails user = this.userDetailsService.loadUserByUsername(username);
            
            UsernamePasswordAuthenticationToken authentication = UsernamePasswordAuthenticationToken
                .authenticated(username, null, user.getAuthorities());
            
            SecurityContextHolder.getContext().setAuthentication(authentication);
            doFilter(request, response, filterChain);

        } catch(JwtException jwtException) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
        
    }

    
}
