package io.github.mehdicharife.missionauthservice.mapper;

import java.util.ArrayList;
import java.util.List;

import io.github.mehdicharife.missionauthservice.domain.AccountCreationRequest;
import io.github.mehdicharife.missionauthservice.domain.Role;
import io.github.mehdicharife.missionauthservice.dto.CreateAccountCreationRequestRequest;
import io.github.mehdicharife.missionauthservice.dto.CreateAccountCreationRequestResponse;

public class AccountCreationRequestMapper {


    public static AccountCreationRequest fromDto(CreateAccountCreationRequestRequest createAccountCreationRequestRequest) {
        AccountCreationRequest accountCreationRequest = new AccountCreationRequest();

        accountCreationRequest.setUsername(createAccountCreationRequestRequest.getUsername());

        accountCreationRequest.setPassword(createAccountCreationRequestRequest.getPassword()); 

        List<Role> roles = new ArrayList<Role>();
        for(String roleName : createAccountCreationRequestRequest.getRolesNames()) {
            roles.add(new Role(roleName));
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
