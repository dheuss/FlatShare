package com.flatshare.domain.datatypes.db.profiles;

import com.flatshare.domain.datatypes.db.DatabaseItem;
import com.google.gson.Gson;

/**
 * Created by Arber on 12/12/2016.
 */
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
