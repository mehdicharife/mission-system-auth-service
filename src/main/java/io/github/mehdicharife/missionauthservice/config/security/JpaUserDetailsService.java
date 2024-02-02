package io.github.mehdicharife.missionauthservice.config.security;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import io.github.mehdicharife.missionauthservice.domain.Account;
import io.github.mehdicharife.missionauthservice.repository.AccountRepository;


@Component
public class JpaUserDetailsService implements UserDetailsService {

    private AccountRepository accountRepository;

    public JpaUserDetailsService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> optionalAccount = this.accountRepository.findByUsername(username);
        if(optionalAccount.isEmpty()) {
            throw new UsernameNotFoundException("Username :" + username + " not found.");
        }

        Account account = optionalAccount.get();
        UserDetails userDetails = User.withUsername(username)
                                      .password(account.getPassword())
                                      .roles(
                                            account
                                                .getRoles()
                                                .stream()
                                                .map(role -> role.getName())
                                                .collect(Collectors.toList())
                                                .toArray(new String[0])
                                            )
                                      .build();
        
        return userDetails;
    }

    
}
