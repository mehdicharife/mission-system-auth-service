package io.github.mehdicharife.missionauthservice.dto;

import java.util.List;

// To futur self: The double Request at the end is intentional and not a typo
public class CreateAccountCreationRequestRequest {
    
    private transient AccountDtoIn accountDetails = new AccountDtoIn();


    public CreateAccountCreationRequestRequest() { }


    public CreateAccountCreationRequestRequest(AccountDtoIn accountDtoIn) {
        this.accountDetails = accountDtoIn;
    }


    public String getUsername() {
        return this.accountDetails.getUsername();
    }

    public void setUsername(String username) {
        this.accountDetails.setUsername(username);
    }


    public String getPassword() {
        return this.accountDetails.getPassword();
    }

    public void setPassword(String password) {
        this.accountDetails.setPassword(password);
    }


    public List<String> getRolesNames() {
        return this.accountDetails.getRolesNames();
    }

    public void setRolesNames(List<String> rolesNames) {
        this.accountDetails.setRolesNames(rolesNames);
    }

}
