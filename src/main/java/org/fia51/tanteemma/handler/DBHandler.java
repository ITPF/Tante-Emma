package org.fia51.tanteemma.handler;

import java.util.List;

/**
 * Created by ffigorstoljarow on 23.05.2017.
 */
public interface DBHandler<T> {
    int createNewTable();
    int dropTable();
    int insertNewEntities(List<T> obj);
    int insertNewEntity(T obj);
}
