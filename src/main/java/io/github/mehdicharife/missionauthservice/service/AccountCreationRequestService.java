package io.github.mehdicharife.missionauthservice.service;

import io.github.mehdicharife.missionauthservice.domain.AccountCreationRequest;
import io.github.mehdicharife.missionauthservice.exception.RoleNotSupportedException;
import io.github.mehdicharife.missionauthservice.exception.UsernameAlreadyExistsException;

public interface AccountCreationRequestService {
    
    AccountCreationRequest createAccountCreationRequest(AccountCreationRequest accountCreationRequest) throws UsernameAlreadyExistsException, RoleNotSupportedException;
}
