package io.github.mehdicharife.missionauthservice.domain;



public class Jwt {
    
    private String content;

    private Long accountId;

    public Jwt() {

    }

    public Jwt(String content) {
        this.content = content;
    }

    public Jwt(String content, Long accountId) {
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

