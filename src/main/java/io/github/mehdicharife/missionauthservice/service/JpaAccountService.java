package io.github.mehdicharife.missionauthservice.service;

import java.util.Optional;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.github.mehdicharife.missionauthservice.domain.Account;
import io.github.mehdicharife.missionauthservice.exception.BadUsernameOrPasswordException;
import io.github.mehdicharife.missionauthservice.repository.AccountRepository;

@Service
public class JpaAccountService implements AccountService {

    private AccountRepository accountRepository;

    private PasswordEncoder passwordEncoder;

    public JpaAccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
    


    public Account findAccountByUsernameAndUnEncodedPassword(String username, String unEncodedPassword) throws BadUsernameOrPasswordException {
        Optional<Account> optionalAccont = this.accountRepository.findByUsername(username);
        
        if(optionalAccont.isPresent()) {
            Account account = optionalAccont.get();
            System.out.println("Account found");
            System.out.println("encoded password: "  + this.passwordEncoder.encode(unEncodedPassword));
            System.out.println("Stored password: " + account.getPassword());
            if(passwordEncoder.matches(unEncodedPassword, account.getPassword())) {
                return account;
            }
        }

        throw new BadUsernameOrPasswordException();
    }
}
