package com.flatshare.domain.datatypes.enums;

/**
 * Created by Arber on 14/01/2017.
 */

public enum DecisionType {
    YES(0), NO(1), PENDING(2);

    int value;

    DecisionType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
