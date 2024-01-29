package io.github.mehdicharife.missionauthservice.dto;

import java.util.ArrayList;
import java.util.List;

public class AccountCreationRequestDto {
    
    private String username;
    
    private String password;

    private List<String> rolesNames = new ArrayList<>();


    public AccountCreationRequestDto() {

    }

    public AccountCreationRequestDto(String username, String password) {
        this.username = username;
        this.password = password;
    }


    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getRolesNames() {
        return this.rolesNames;
    }

    public void setRolesNames(List<String> rolesNames) {
        this.rolesNames = rolesNames;
    }

}
