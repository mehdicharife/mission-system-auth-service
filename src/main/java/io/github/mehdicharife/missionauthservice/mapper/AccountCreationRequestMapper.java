package io.github.mehdicharife.missionauthservice.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import io.github.mehdicharife.missionauthservice.domain.AccountCreationRequest;
import io.github.mehdicharife.missionauthservice.domain.Role;
import io.github.mehdicharife.missionauthservice.dto.AccountCreationRequestDto;
import io.github.mehdicharife.missionauthservice.repository.RoleRepository;

public class AccountCreationRequestMapper {

    private static RoleRepository roleRepository;


    public AccountCreationRequestMapper(RoleRepository repository) {
        roleRepository = repository;
    }


    public static AccountCreationRequest fromDto(AccountCreationRequestDto accountCreationRequestDto) {
        AccountCreationRequest accountCreationRequest = new AccountCreationRequest();

        accountCreationRequest.setUsername(accountCreationRequestDto.getUsername());

        accountCreationRequest.setPassword(accountCreationRequestDto.getPassword()); 

        List<Role> roles = new ArrayList<Role>();
        for(String roleName : accountCreationRequestDto.getRolesNames()) {
            Optional<Role> optionalRole = roleRepository.findByName(roleName);
            if(optionalRole.isPresent()) {
                roles.add(optionalRole.get());
            }
        }
        accountCreationRequest.setRoles(roles);

        return accountCreationRequest;
    }
    
}
