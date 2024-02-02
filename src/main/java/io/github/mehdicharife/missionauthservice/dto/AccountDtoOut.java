package io.github.mehdicharife.missionauthservice.dto;

import java.util.ArrayList;
import java.util.List;

public class AccountDtoOut {

    private Long id;

    private String username;

    private List<String> rolesNames = new ArrayList<>();


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

    public List<String> getRolesNames() {
        return this.rolesNames;
    }

    public void setRolesNames(List<String> rolesNames) {
        this.rolesNames = rolesNames;
    }

    
}
