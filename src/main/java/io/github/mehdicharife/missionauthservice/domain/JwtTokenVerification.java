package io.github.mehdicharife.missionauthservice.domain;


public class JwtTokenVerification {
    
    private JwtToken jwtToken;

    private boolean isSuccessfull;


    public JwtTokenVerification(JwtToken jwtToken, boolean isSuccessfull) {
        this.jwtToken = jwtToken;
        this.isSuccessfull = isSuccessfull;
    }

    public JwtTokenVerification(JwtToken jwtToken) {
        this.jwtToken = jwtToken;
    }

    public JwtTokenVerification() {
    }


    public JwtToken getJwtToken() {
        return this.jwtToken;
    }

    public void setJwtToken(JwtToken jwtToken) {
        this.jwtToken = jwtToken;
    }

    public boolean isIsSuccessfull() {
        return this.isSuccessfull;
    }

    public boolean getIsSuccessfull() {
        return this.isSuccessfull;
    }

    public void setIsSuccessfull(boolean isSuccessfull) {
        this.isSuccessfull = isSuccessfull;
    }

}
