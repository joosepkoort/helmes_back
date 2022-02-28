package com.helmes.proovitoo;

import com.helmes.proovitoo.dao.SetupDao;
import com.helmes.proovitoo.util.PropertyLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@SpringBootApplication
public class Main {
    private static final Logger log = LogManager.getLogger(Main.class);

    public static String dbUrl = PropertyLoader.getProperty("javax.persistence.jdbc.url");

    public static void main(String[] args) {
        log.info("schema loaded");

        try {
            SetupDao dao = new SetupDao();
            Connection c = DriverManager.getConnection(dbUrl);
            dao.createSchema(c);
            //new org.hsqldb.util.DatabaseManagerSwing().main(new String[]{"--url", dbUrl, "--user", "sa", "--password", ""}); //käivitab andmebaasi GUI
            SpringApplication.run(Main.class, args);
            dao.createTreeFromFileAndInsertToDatabase();
            log.info("tree inserted to DB");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
