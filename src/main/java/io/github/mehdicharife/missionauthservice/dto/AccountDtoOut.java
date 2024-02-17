package io.github.mehdicharife.missionauthservice.dto;

import java.util.ArrayList;
import java.util.List;

public class AccountDtoOut {

    private Long id;

    private String username;

    private List<String> roles = new ArrayList<>();


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getRoles() {
        return this.roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    
}
