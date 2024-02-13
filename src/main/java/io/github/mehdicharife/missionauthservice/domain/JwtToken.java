package io.github.mehdicharife.missionauthservice.domain;



public class JwtToken {
    
    private String content;

    private Long accountId;

    public JwtToken() {

    }

    public JwtToken(String content) {
        this.content = content;
    }

    public JwtToken(String content, Long accountId) {
        this.content = content;
        this.accountId = accountId;
    }



    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getAccountId() {
        return this.accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }
   
}

