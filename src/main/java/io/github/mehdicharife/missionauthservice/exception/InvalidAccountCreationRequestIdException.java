package io.github.mehdicharife.missionauthservice.exception;

public class InvalidAccountCreationRequestIdException extends Exception {

    public InvalidAccountCreationRequestIdException(Long id) {
        super("There is no account creation request with the ID: " + id);
    }
    
}
