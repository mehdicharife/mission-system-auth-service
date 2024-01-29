package io.github.mehdicharife.missionauthservice.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Embeddable;

import jakarta.persistence.ManyToMany;

@Embeddable
public class AccountDetails {

    private String username;

    private String password;

    private Long userId;

    @ManyToMany
    private List<Role> roles = new ArrayList<Role>();


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

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }


    public List<Role> getRoles() {
        return this.roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

}
