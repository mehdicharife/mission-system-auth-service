package io.github.mehdicharife.missionauthservice.exception;

public class BadUsernameOrPasswordException extends Exception {
    
    public BadUsernameOrPasswordException() {
        super("Wrong username or password");
    }
}
