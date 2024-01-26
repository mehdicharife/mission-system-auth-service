package io.github.mehdicharife.missionauthservice.exception;

public class UsernameAlreadyExistsException extends Exception{
    
    public UsernameAlreadyExistsException() {

    }

    public UsernameAlreadyExistsException(String username) {
        super("Username: " + username + " already exists");
    }
}
