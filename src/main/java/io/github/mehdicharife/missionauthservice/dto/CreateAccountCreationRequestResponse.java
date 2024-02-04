package io.github.mehdicharife.missionauthservice.dto;

import java.util.List;

public class CreateAccountCreationRequestResponse {

    private Long id;

    private transient AccountDtoOut accountDetails = new AccountDtoOut();


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getUsername() {
        return this.accountDetails.getUsername();
    }

    public void setUsername(String username) {
        this.accountDetails.setUsername(username);
    }

    public List<String> getRolesNames() {
        return this.accountDetails.getRolesNames();
    }

    public void setRolesNames(List<String> rolesNames) {
        this.accountDetails.setRolesNames(rolesNames);
    }

    
}
