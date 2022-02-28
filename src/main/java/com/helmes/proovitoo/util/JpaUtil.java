package com.helmes.proovitoo.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaUtil {

    private static EntityManagerFactory emf;

    public static EntityManagerFactory getFactory() {
        if (emf != null) {
            return emf;
        }

        emf = Persistence.createEntityManagerFactory("my-hsql-unit",
                            new PropertyLoader().getPropertiesAsMap());

        return emf;
    }

    public static void closeFactory() {
        if (emf != null) {
            emf.close();
        }
        org.hsqldb.DatabaseManager.closeDatabases(3);
    }

    public static void closeQuietly(EntityManager em) {
        if (em != null) {
            em.close();
        }
    }

}
