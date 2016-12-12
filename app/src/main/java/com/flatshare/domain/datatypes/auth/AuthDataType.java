package com.flatshare.domain.datatypes.auth;

/**
 * Created by Arber on 06/12/2016.
 */

class AuthDataType {

    private String email;
    private String password;

    public AuthDataType(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email.trim();
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password.trim();
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
