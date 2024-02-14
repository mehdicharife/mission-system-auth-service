package io.github.mehdicharife.missionauthservice.config.security.filters;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import io.github.mehdicharife.missionauthservice.domain.Account;
import io.github.mehdicharife.missionauthservice.domain.Jwt;
import io.github.mehdicharife.missionauthservice.domain.JwtVerification;
import io.github.mehdicharife.missionauthservice.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class JwtCookieFilter extends OncePerRequestFilter{

    private final String JWT_COOKIE_NAME;

    private JwtService jwtService;

    public JwtCookieFilter(@Value("${cookies.jwt.name}") String JWT_COOKIE_NAME, JwtService jwtService) {
        this.JWT_COOKIE_NAME = JWT_COOKIE_NAME;
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        Cookie jwtCookie = WebUtils.getCookie(request, JWT_COOKIE_NAME);
        if(jwtCookie == null || jwtCookie.getValue() == null) {
            filterChain.doFilter(request, response);
            return;
        }

        Jwt jwt = new Jwt(jwtCookie.getValue());
        JwtVerification jwtVerification = this.jwtService.verifyToken(jwt);

        if(!jwtVerification.isSuccessfull()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        List<GrantedAuthority> authorities = this.jwtService
            .getRolesFromJwt(jwt)
            .stream()
            .map(role -> 
                new GrantedAuthority() {
                    public String getAuthority() {
                        return "ROLE_" + role.getName();
                    }
                }
            )
            .collect(Collectors.toList());

        
        Account principal = new Account();
        principal.setId(jwtService.extractId(jwt.getContent()));
        Authentication authentication = UsernamePasswordAuthenticationToken.authenticated(principal, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }
     
}
