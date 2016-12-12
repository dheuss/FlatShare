package com.flatshare.domain.datatypes.auth;

/**
 * Created by Arber on 06/12/2016.
 */

public class RegisterDataType extends AuthDataType{

    String name;

    public RegisterDataType(String name, String email, String password) {
        super(email, password);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
