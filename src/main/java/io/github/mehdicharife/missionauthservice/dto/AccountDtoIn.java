package io.github.mehdicharife.missionauthservice.dto;

import java.util.ArrayList;
import java.util.List;

public class AccountDtoIn {

    private String username;

    private String password;

    private List<String> roles = new ArrayList<>();


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

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    
    
}
