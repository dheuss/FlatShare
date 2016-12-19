package com.flatshare.domain.datatypes.auth;

/**
 * Created by david on 19.12.2016.
 */

public class ResetDataType {

    String email;

    public ResetDataType (String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
