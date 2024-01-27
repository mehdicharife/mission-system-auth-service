package io.github.mehdicharife.missionauthservice.exception;

public class InvalidPasswordException extends Exception {
    public InvalidPasswordException() {
        super("Incorrect Password.");
    }
}
