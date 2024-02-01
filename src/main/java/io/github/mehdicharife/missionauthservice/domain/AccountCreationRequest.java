package io.github.mehdicharife.missionauthservice.domain;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

// Not a DTO
@Entity
@Table(name = "account_creation_requests")
public class AccountCreationRequest {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    private AccountDetails accountDetails;


    public AccountCreationRequest() {
    
    }

    public AccountCreationRequest(Long id, AccountDetails accountDetails) {
        this.id = id;
        this.accountDetails = accountDetails;
    }


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AccountDetails getAccountDetails() {
        return this.accountDetails;
    }

    public void setAccountDetails(AccountDetails accountDetails) {
        this.accountDetails = accountDetails;
    }

    public String getUsername() {
        this.initializeAccountDetailsIfNull();
        return this.accountDetails.getUsername();
    }

    public void setUsername(String username) {
        this.initializeAccountDetailsIfNull();
        this.accountDetails.setUsername(username);
    }

    public String getPassword() {
        this.initializeAccountDetailsIfNull();
        return this.accountDetails.getPassword();
    }

    public void setPassword(String password) {
        this.initializeAccountDetailsIfNull();
        this.accountDetails.setPassword(password);
    }

    public Long getUserId() {
        this.initializeAccountDetailsIfNull();
        return this.accountDetails.getUserId();
    }

    public void setUserId(Long userId) {
        this.initializeAccountDetailsIfNull();
        this.accountDetails.setUserId(userId);
    }


    public List<Role> getRoles() {
        this.initializeAccountDetailsIfNull();
        return this.accountDetails.getRoles();
    }

    public void setRoles(List<Role> roles) {
        this.initializeAccountDetailsIfNull();
        this.accountDetails.setRoles(roles);
    }

    private void initializeAccountDetailsIfNull() {
        if(accountDetails == null) {
            this.accountDetails = new AccountDetails();
        }   
    }
}
