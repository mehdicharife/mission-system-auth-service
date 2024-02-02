package io.github.mehdicharife.missionauthservice.service;

import java.util.Optional;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.github.mehdicharife.missionauthservice.domain.Account;
import io.github.mehdicharife.missionauthservice.domain.AccountCreationRequest;
import io.github.mehdicharife.missionauthservice.exception.BadUsernameOrPasswordException;
import io.github.mehdicharife.missionauthservice.exception.InvalidAccountCreationRequestIdException;
import io.github.mehdicharife.missionauthservice.repository.AccountCreationRequestRepository;
import io.github.mehdicharife.missionauthservice.repository.AccountRepository;

@Service
public class JpaAccountService implements AccountService {

    private AccountRepository accountRepository;

    private AccountCreationRequestRepository accountCreationRequestRepository;

    private PasswordEncoder passwordEncoder;

    public JpaAccountService(AccountRepository accountRepository, AccountCreationRequestRepository accountCreationRequestRepository) {
        this.accountRepository = accountRepository;
        this.accountCreationRequestRepository = accountCreationRequestRepository;
        this.passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
    

    public Account findAccountByUsernameAndUnEncodedPassword(String username, String unEncodedPassword) throws BadUsernameOrPasswordException {
        Optional<Account> optionalAccont = this.accountRepository.findByUsername(username);
        
        if(optionalAccont.isPresent()) {
            Account account = optionalAccont.get();
            if(passwordEncoder.matches(unEncodedPassword, account.getPassword())) {
                return account;
            }
        }

        throw new BadUsernameOrPasswordException();
    }

    public Account createAccountBasedOnRequestId(Long id) throws InvalidAccountCreationRequestIdException {
        Optional<AccountCreationRequest> optionalAccountCreationRequest = this.accountCreationRequestRepository.findById(id);
        if(!optionalAccountCreationRequest.isPresent()) {
            throw new InvalidAccountCreationRequestIdException(id);
        }
        
        AccountCreationRequest request = optionalAccountCreationRequest.get();
        Account account = new Account(request.getAccountDetails());
        
        return this.accountRepository.save(account);
    }
}
