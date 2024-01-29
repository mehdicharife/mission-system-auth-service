package io.github.mehdicharife.missionauthservice.domain;


import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "accounts")
public class Account {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private AccountDetails accountDetails;


    public Account() {

    }
    
    public Account(AccountDetails accountDetails) {
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

    public Long getUserId() {
        return this.accountDetails.getUserId();
    }

    public void setUserId(Long userId) {
        this.accountDetails.setUserId(userId);
    }


    public List<Role> getRoles() {
        return this.accountDetails.getRoles();
    }

    public void setRoles(List<Role> roles) {
        this.accountDetails.getRoles();
    }

}
