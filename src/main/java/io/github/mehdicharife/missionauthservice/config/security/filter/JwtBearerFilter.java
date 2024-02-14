package io.github.mehdicharife.missionauthservice.config.security.filter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.github.mehdicharife.missionauthservice.config.security.mapper.RolesAuthoritiesMapper;
import io.github.mehdicharife.missionauthservice.domain.Account;
import io.github.mehdicharife.missionauthservice.domain.Jwt;
import io.github.mehdicharife.missionauthservice.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class JwtBearerFilter extends OncePerRequestFilter  {

    private JwtService jwtService;

    public JwtBearerFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }


    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        if(authorizationHeader == null || !authorizationHeader.startsWith("Bearer")) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwtString = authorizationHeader.substring("Bearer".length());
        Optional<Account> optionalAccount = this.jwtService.extractAccountFromJwt(new Jwt(jwtString));
        if(optionalAccount.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        Account account = optionalAccount.get();
        List<GrantedAuthority> authorities = RolesAuthoritiesMapper.toAuthorities(account.getRoles());
        
        UsernamePasswordAuthenticationToken authentication = UsernamePasswordAuthenticationToken
            .authenticated(account, null, authorities);
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        doFilter(request, response, filterChain);

        
    }

    
}
