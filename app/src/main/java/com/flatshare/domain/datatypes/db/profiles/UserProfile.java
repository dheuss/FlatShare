package com.flatshare.domain.datatypes.db.profiles;

import com.flatshare.domain.datatypes.db.DatabaseItem;
import com.google.gson.Gson;

/**
 * Created by Arber on 06/12/2016.
 */

public class UserProfile implements DatabaseItem {

    @Override
    public String toString() {

        Gson gson = new Gson();
        String jsonInString = gson.toJson(this);

        return jsonInString;

    }

}
