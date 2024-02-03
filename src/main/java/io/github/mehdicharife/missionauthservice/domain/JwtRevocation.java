package io.github.mehdicharife.missionauthservice.domain;



public class JwtRevocation {

    String jwt;

    public JwtRevocation() {

    }
    
    public JwtRevocation(String jwt) {
        this.jwt = jwt;
    }


    public String getJwt() {
        return this.jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

}
