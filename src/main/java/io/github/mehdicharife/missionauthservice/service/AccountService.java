package io.github.mehdicharife.missionauthservice.service;

import io.github.mehdicharife.missionauthservice.domain.Account;
import io.github.mehdicharife.missionauthservice.exception.BadUsernameOrPasswordException;
import io.github.mehdicharife.missionauthservice.exception.InvalidAccountCreationRequestIdException;

public interface AccountService {

    Account findAccountByUsernameAndUnEncodedPassword(String username, String password) throws BadUsernameOrPasswordException;
    Account createAccountBasedOnRequestId(Long id) throws InvalidAccountCreationRequestIdException;
}
