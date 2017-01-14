package com.flatshare.domain.datatypes.enums;

/**
 * Created by Arber on 08/01/2017.
 */
public enum ProfileType {
    TENANT(0), ROOMMATE(1), APARTMENT(2);

    int value;

    ProfileType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
