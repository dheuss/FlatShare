package com.flatshare.network;

import com.google.firebase.database.DatabaseException;

import com.flatshare.domain.datatypes.db.DatabaseItem;

/**
 * Created by Arber on 06/12/2016.
 */

public interface DatabaseManager {

    void create(DatabaseItem databaseItem, String path) throws DatabaseException;

    DatabaseItem read(String path, Class<? extends DatabaseItem> databaseItemClass) throws DatabaseException;

    void update(DatabaseItem newDatabaseItem, String path) throws DatabaseException;

    void delete(String path) throws DatabaseException;

    void addJsonRoot(String path, String id) throws DatabaseException;

    String getUserId();

}
