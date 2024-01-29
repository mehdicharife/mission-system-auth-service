package io.github.mehdicharife.missionauthservice.domain;

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

    @OneToOne
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

}
