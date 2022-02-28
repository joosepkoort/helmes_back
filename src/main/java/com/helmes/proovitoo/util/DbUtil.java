package com.helmes.proovitoo.util;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DbUtil {

    public static void insertFromString(Connection connection, String statementsAsString) {
        try (Statement stmt = connection.createStatement()) {
            for (String sql : getSqlStatements(statementsAsString)) {
                stmt.executeUpdate(sql);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static List<String> getSqlStatements(String statements) {
        return Stream.of(statements.split(";"))
                .filter(line -> !line.trim().isEmpty())
                .collect(Collectors.toList());
    }


}
