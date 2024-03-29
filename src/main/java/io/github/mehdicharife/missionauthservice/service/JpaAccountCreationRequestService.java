package io.github.mehdicharife.missionauthservice.service;


import java.util.Optional;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.github.mehdicharife.missionauthservice.domain.Account;
import io.github.mehdicharife.missionauthservice.domain.AccountCreationRequest;
import io.github.mehdicharife.missionauthservice.domain.Role;
import io.github.mehdicharife.missionauthservice.exception.RoleNotSupportedException;
import io.github.mehdicharife.missionauthservice.exception.UsernameAlreadyExistsException;
import io.github.mehdicharife.missionauthservice.repository.AccountCreationRequestRepository;
import io.github.mehdicharife.missionauthservice.repository.AccountRepository;
import io.github.mehdicharife.missionauthservice.repository.RoleRepository;

@Service
public class JpaAccountCreationRequestService  implements AccountCreationRequestService {

    private final AccountCreationRequestRepository accountCreationRequestRepository;
    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;


    public JpaAccountCreationRequestService(AccountCreationRequestRepository accountCreationRequestRepository,
                                            AccountRepository accountRepository,
                                            RoleRepository roleRepository) {
        this.accountCreationRequestRepository = accountCreationRequestRepository;
        this.accountRepository = accountRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    public AccountCreationRequest createAccountCreationRequest(AccountCreationRequest accountCreationRequest) 
            throws UsernameAlreadyExistsException, RoleNotSupportedException {

        String username = accountCreationRequest.getUsername();
        Optional<Account> optionalAccoount = this.accountRepository.findByUsername(username);
        if(optionalAccoount.isPresent()) {
            throw new UsernameAlreadyExistsException(username);
        }

        for(Role role : accountCreationRequest.getRoles()) {
            Optional<Role> optionalRole = roleRepository.findByName(role.getName());
            if(optionalRole.isEmpty()) {
                throw new RoleNotSupportedException(role.getName());
            } else {
                role.setId(optionalRole.get().getId());
            }
        }

        accountCreationRequest.setPassword(passwordEncoder.encode(accountCreationRequest.getPassword()));
        return this.accountCreationRequestRepository.save(accountCreationRequest);

    }
    
    
}
