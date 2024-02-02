package io.github.mehdicharife.missionauthservice.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import io.github.mehdicharife.missionauthservice.domain.AccountCreationRequest;
import io.github.mehdicharife.missionauthservice.domain.Role;
import io.github.mehdicharife.missionauthservice.dto.CreateAccountCreationRequestRequest;
import io.github.mehdicharife.missionauthservice.dto.CreateAccountCreationRequestResponse;
import io.github.mehdicharife.missionauthservice.repository.RoleRepository;

public class AccountCreationRequestMapper {

    private static RoleRepository roleRepository;


    public AccountCreationRequestMapper(RoleRepository repository) {
        roleRepository = repository;
    }


    public static AccountCreationRequest fromDto(CreateAccountCreationRequestRequest createAccountCreationRequestRequest) {
        AccountCreationRequest accountCreationRequest = new AccountCreationRequest();

        accountCreationRequest.setUsername(createAccountCreationRequestRequest.getUsername());

        accountCreationRequest.setPassword(createAccountCreationRequestRequest.getPassword()); 

        List<Role> roles = new ArrayList<Role>();
        for(String roleName : createAccountCreationRequestRequest.getRolesNames()) {
            Optional<Role> optionalRole = roleRepository.findByName(roleName);
            if(optionalRole.isPresent()) {
                roles.add(optionalRole.get());
            }
        }
        accountCreationRequest.setRoles(roles);

        return accountCreationRequest;
    }

    public static CreateAccountCreationRequestResponse toDto(AccountCreationRequest accountCreationRequest) {
        CreateAccountCreationRequestResponse response = new CreateAccountCreationRequestResponse();

        response.setUsername(accountCreationRequest.getUsername());
        
        response.setId(accountCreationRequest.getId());
        
        for(Role role: accountCreationRequest.getRoles()) {
            response.getRolesNames().add(role.getName());
        }
        
        return response;
    }
    
}
