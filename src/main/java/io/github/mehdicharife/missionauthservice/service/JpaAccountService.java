package io.github.mehdicharife.missionauthservice.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import io.github.mehdicharife.missionauthservice.domain.Account;
import io.github.mehdicharife.missionauthservice.exception.UsernameAlreadyExistsException;
import io.github.mehdicharife.missionauthservice.repository.AccountRepository;

@Service
public class JpaAccountService implements AccountService {

    private AccountRepository accountRepository;

    public JpaAccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }
    
    public Account createAccount(String username, String password) throws UsernameAlreadyExistsException {
        Optional<Account> optionalAccount = this.accountRepository.findByUsername(username);
        if(optionalAccount.isPresent()) {
            throw new UsernameAlreadyExistsException(username);
        }

        Account account = new Account(username, password);

        return this.accountRepository.save(account);
    }

}
