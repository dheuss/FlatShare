package com.flatshare.domain.datatypes.auth;

/**
 * Created by david on 02.01.2017.
 */

public class ChangePasswordDataType {

    String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ChangePasswordDataType(String password) {

        this.password = password;
    }
}
