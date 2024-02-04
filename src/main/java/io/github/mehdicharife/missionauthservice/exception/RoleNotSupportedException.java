package io.github.mehdicharife.missionauthservice.exception;

public class RoleNotSupportedException extends Exception {

    public RoleNotSupportedException(String roleName) {
        super(roleName + " not supported");
    }
    
}
