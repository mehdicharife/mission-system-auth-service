package io.github.mehdicharife.missionauthservice.service;


import java.util.Optional;

import org.springframework.stereotype.Service;

import io.github.mehdicharife.missionauthservice.domain.Account;
import io.github.mehdicharife.missionauthservice.domain.AccountCreationRequest;
import io.github.mehdicharife.missionauthservice.exception.UsernameAlreadyExistsException;
import io.github.mehdicharife.missionauthservice.repository.AccountCreationRequestRepository;
import io.github.mehdicharife.missionauthservice.repository.AccountRepository;

@Service
public class JpaAccountCreationRequestService  implements AccountCreationRequestService {

    private AccountCreationRequestRepository accountCreationRequestRepository;
    private AccountRepository accountRepository;


    public JpaAccountCreationRequestService(AccountCreationRequestRepository accountCreationRequestRepository,
                                            AccountRepository accountRepository) {
        this.accountCreationRequestRepository = accountCreationRequestRepository;
        this.accountRepository = accountRepository;
    }

    public AccountCreationRequest createAccountCreationRequest(AccountCreationRequest accountCreationRequest) throws UsernameAlreadyExistsException {
        String username = accountCreationRequest.getUsername();
        Optional<Account> optionalAccoount = this.accountRepository.findByUsername(username);
        if(optionalAccoount.isPresent()) {
            throw new UsernameAlreadyExistsException(username);
        }

        AccountCreationRequest request =  this.accountCreationRequestRepository.save(accountCreationRequest);

        return request;
    }
    
    
}
