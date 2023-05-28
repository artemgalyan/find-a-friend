package by.fpmibsu.findafriend.application.authentication;

import org.jose4j.jwt.JwtClaims;

public class AuthenticationData {
    private JwtClaims claims;
    private boolean isValid;
    private String token;

    public AuthenticationData(JwtClaims claims, boolean isValid, String token) {
        this.claims = claims;
        this.isValid = isValid;
        this.token = token;
    }

    public JwtClaims getClaims() {
        return claims;
    }

    public void setClaims(JwtClaims claims) {
        this.claims = claims;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getClaim(String key) {
        return claims.getClaimValueAsString(key);
    }
}
