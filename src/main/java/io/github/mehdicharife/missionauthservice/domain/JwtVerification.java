package io.github.mehdicharife.missionauthservice.domain;


public class JwtVerification {
    
    private Jwt jwtToken;

    private boolean isSuccessfull;


    public JwtVerification(Jwt jwtToken, boolean isSuccessfull) {
        this.jwtToken = jwtToken;
        this.isSuccessfull = isSuccessfull;
    }

    public JwtVerification(Jwt jwtToken) {
        this.jwtToken = jwtToken;
    }

    public JwtVerification() {
    }


    public Jwt getJwtToken() {
        return this.jwtToken;
    }

    public void setJwtToken(Jwt jwtToken) {
        this.jwtToken = jwtToken;
    }



    public boolean isSuccessfull() {
        return this.isSuccessfull;
    }

    public void setIsSuccessfull(boolean isSuccessfull) {
        this.isSuccessfull = isSuccessfull;
    }

}
