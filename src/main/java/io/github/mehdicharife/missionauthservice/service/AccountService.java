package io.github.mehdicharife.missionauthservice.service;

import io.github.mehdicharife.missionauthservice.domain.Account;
import io.github.mehdicharife.missionauthservice.exception.BadUsernameOrPasswordException;
import io.github.mehdicharife.missionauthservice.exception.UsernameAlreadyExistsException;

public interface AccountService {

    Account createAccount(String username, String password) throws UsernameAlreadyExistsException;

    Account findAccountByUsernameAndUnEncodedPassword(String username, String password) throws BadUsernameOrPasswordException;
    
}
