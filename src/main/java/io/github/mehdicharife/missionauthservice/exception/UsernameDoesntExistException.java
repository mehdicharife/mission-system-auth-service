package io.github.mehdicharife.missionauthservice.exception;


public class UsernameDoesntExistException extends Exception{
    public UsernameDoesntExistException(String username) {
        super("Username :" + username + " doesn't exist.");
    }
}
