package io.github.mehdicharife.missionauthservice.domain;



public class JwtToken {
    
    private String content;


    public JwtToken(String content) {
        this.content = content;
    }



    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
