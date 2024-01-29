package io.github.mehdicharife.missionauthservice.service;

import io.github.mehdicharife.missionauthservice.domain.Account;
import io.github.mehdicharife.missionauthservice.exception.BadUsernameOrPasswordException;

public interface AccountService {

    Account findAccountByUsernameAndUnEncodedPassword(String username, String password) throws BadUsernameOrPasswordException;
    
}
