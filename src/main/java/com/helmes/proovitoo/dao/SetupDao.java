package com.helmes.proovitoo.dao;

import com.helmes.proovitoo.model.Mapping;
import com.helmes.proovitoo.model.HtmlRelation;
import com.helmes.proovitoo.util.DbUtil;
import com.helmes.proovitoo.util.FileUtil;
import com.helmes.proovitoo.util.NodeUtils;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SetupDao implements SetupDaoInterface {

    @Override
    public void createSchema(Connection c) {
        String statements = FileUtil.readFileFromClasspath("schema.sql");
        DbUtil.insertFromString(c, statements);
    }

    /**
     * Reads index.html file with utf-8 encoding and parses the multiselect element
     */
    public void createTreeFromFileAndInsertToDatabase() {
        List<HtmlRelation> relationsList = null;
        try {
            relationsList = FileUtil.parseMultiSelect("utf-8", "index.html");
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Mapping> list = new ArrayList<Mapping>();
        for (HtmlRelation relation : relationsList) {
            list.add(new Mapping(relation.getParentId(), relation.getId(), relation.getValue()));
        }
        insertTreeIntoDatabase(list);
    }

    @Override
    public void insertTreeIntoDatabase(List<Mapping> list) {
        try {
            new NodeUtils().insertTreeIntoDatabase(list);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
