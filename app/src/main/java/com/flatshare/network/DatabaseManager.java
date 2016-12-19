package com.flatshare.network;

import com.flatshare.domain.datatypes.db.DatabaseItem;

import java.util.List;

/**
 * Created by Arber on 06/12/2016.
 */

public interface DatabaseManager {

    boolean create(Object item, String path);

    String push(Object item, String path);

    DatabaseItem readItem(String path, Class<? extends DatabaseItem> databaseItemClass);

    List<String> readIds(String path);

    boolean update(Object newItem, String path);

    boolean delete(String path);

    //Object addListener
}
