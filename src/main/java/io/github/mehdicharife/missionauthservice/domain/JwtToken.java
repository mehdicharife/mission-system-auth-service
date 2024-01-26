package io.github.mehdicharife.missionauthservice.domain;



public class JwtToken {
    
    private String content;


    private JwtToken(String content) {
        this.content = content;
    }

}
