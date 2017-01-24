package com.flatshare.domain.datatypes.db.profiles;

import com.flatshare.domain.datatypes.db.DatabaseItem;
import com.google.gson.Gson;

public abstract class UserProfile implements DatabaseItem {

    public abstract String getId();
    public abstract int getType();

    @Override
    public String toString() {
        Gson gson = new Gson();
        String jsonInString = gson.toJson(this);
        return jsonInString;
    }

    public abstract void setId(String id);
}
