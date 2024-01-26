package io.github.mehdicharife.missionauthservice.event;

public class UserDetailsCreatedForAccountEvent {

    private Long userId;

    private Long accountId;

    public UserDetailsCreatedForAccountEvent() {
    }

    public UserDetailsCreatedForAccountEvent(Long userId) {
        this.userId = userId;
    }


    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }


    public Long getAccountId() {
        return this.accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    
}
