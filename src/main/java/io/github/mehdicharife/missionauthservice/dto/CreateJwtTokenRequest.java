package io.github.mehdicharife.missionauthservice.dto;



public class CreateJwtTokenRequest {

    private String username;

    private String password;


    public CreateJwtTokenRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public CreateJwtTokenRequest() {
    }


    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    
}
