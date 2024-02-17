package io.github.mehdicharife.missionauthservice.mapper;

import io.github.mehdicharife.missionauthservice.domain.Account;
import io.github.mehdicharife.missionauthservice.domain.Role;
import io.github.mehdicharife.missionauthservice.dto.AccountDtoOut;

public class AccountMapper {

    public static AccountDtoOut toDto(Account account) {
        AccountDtoOut dto = new AccountDtoOut();
        
        dto.setId(account.getId());

        dto.setUsername(account.getUsername());

        for(Role role: account.getRoles()) {
            dto.getRoles().add(role.getName());
        }

        return dto;
    }
    
}
