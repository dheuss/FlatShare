package com.flatshare.network;

import com.flatshare.domain.datatypes.db.DatabaseItem;

/**
 * Created by Arber on 06/12/2016.
 */

public interface DatabaseManager {

    boolean create(DatabaseItem databaseItem, String path);

    DatabaseItem read(String path, Class<? extends DatabaseItem> databaseItemClass);

    boolean update(DatabaseItem newDatabaseItem, String path);

    boolean delete(String path);

    boolean addJsonRoot(String path, String id);

    String getUserId();

}
