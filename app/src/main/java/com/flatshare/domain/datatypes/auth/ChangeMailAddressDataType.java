package com.flatshare.domain.datatypes.auth;

/**
 * Created by david on 02.01.2017.
 */

public class ChangeMailAddressDataType {

    String email;

    public ChangeMailAddressDataType(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
