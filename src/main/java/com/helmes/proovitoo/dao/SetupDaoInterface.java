package com.helmes.proovitoo.dao;

import com.helmes.proovitoo.model.Mapping;
import java.sql.Connection;
import java.util.List;

public interface SetupDaoInterface {
    void createSchema(Connection c);

    void insertTreeIntoDatabase(List<Mapping> list);

    void createTreeFromFileAndInsertToDatabase();
}
