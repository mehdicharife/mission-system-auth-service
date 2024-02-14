package io.github.mehdicharife.missionauthservice.domain;


import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "accounts")
public class Account {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="accountIdGenerator")
    @SequenceGenerator(name="accountIdGenerator", sequenceName = "accounts_id_seq", allocationSize = 1)
    @Column(name = "id")
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
        initializeAccountDetailsIfNull();
        return this.accountDetails.getUsername();
    }

    public void setUsername(String username) {
        initializeAccountDetailsIfNull();
        this.accountDetails.setUsername(username);
    }

    public String getPassword() {
        initializeAccountDetailsIfNull();
        return this.accountDetails.getPassword();
    }

    public void setPassword(String password) {
        initializeAccountDetailsIfNull();
        this.accountDetails.setPassword(password);
    }

    public Long getUserId() {
        initializeAccountDetailsIfNull();
        return this.accountDetails.getUserId();
    }

    public void setUserId(Long userId) {
        initializeAccountDetailsIfNull();
        this.accountDetails.setUserId(userId);
    }


    public List<Role> getRoles() {
        initializeAccountDetailsIfNull();
        return this.accountDetails.getRoles();
    }

    public void setRoles(List<Role> roles) {
        initializeAccountDetailsIfNull();
        this.accountDetails.setRoles(roles);
    }


    private void initializeAccountDetailsIfNull() {
        if(this.accountDetails == null) {
            this.accountDetails = new AccountDetails();
        }
    }

}
