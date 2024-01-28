package io.github.mehdicharife.missionauthservice.service;

import java.util.Optional;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.github.mehdicharife.missionauthservice.domain.Account;
import io.github.mehdicharife.missionauthservice.exception.BadUsernameOrPasswordException;
import io.github.mehdicharife.missionauthservice.exception.UsernameAlreadyExistsException;
import io.github.mehdicharife.missionauthservice.repository.AccountRepository;

@Service
public class JpaAccountService implements AccountService {

    private AccountRepository accountRepository;

    private PasswordEncoder passwordEncoder;

    public JpaAccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
        //this.passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        this.passwordEncoder = NoOpPasswordEncoder.getInstance();
    }
    
    public Account createAccount(String username, String password) throws UsernameAlreadyExistsException {
        Optional<Account> optionalAccount = this.accountRepository.findByUsername(username);
        if(optionalAccount.isPresent()) {
            throw new UsernameAlreadyExistsException(username);
        }

        Account account = new Account(username, password);

        return this.accountRepository.save(account);
    }

    public Account findAccountByUsernameAndUnEncodedPassword(String username, String unEncodedPassword) throws BadUsernameOrPasswordException {
        Optional<Account> optionalAccont = this.accountRepository.findByUsername(username);
        
        if(optionalAccont.isPresent()) {
            Account account = optionalAccont.get();
            if(account.getPassword().equals(this.passwordEncoder.encode(unEncodedPassword))) {
                return account;
            }
        }

        throw new BadUsernameOrPasswordException();
    }
}
