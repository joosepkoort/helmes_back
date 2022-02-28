package com.helmes.proovitoo.dao;

import com.helmes.proovitoo.model.User;
import com.helmes.proovitoo.util.JpaUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

@Repository
public class UserDao implements UserDaoInterface {
    @PersistenceContext
    private EntityManager em;

    protected final Log logger = LogFactory.getLog(getClass());

    @Override
    public User findUserByUserName(String username) {
        em = null;

        try {
            em = JpaUtil.getFactory().createEntityManager();
            TypedQuery<User> query = em.createQuery("select p from User p where username = :username", User.class);
            query.setParameter("username", username);
            return query.getSingleResult();

        } finally {
            JpaUtil.closeQuietly(em);
        }
    }


    @Override
    @Transactional
    public void addRole(User user) {
        String username = user.getUserName();
        em = null;
        try {
            em = JpaUtil.getFactory().createEntityManager();
            em.getTransaction().begin();
            Query query = em.createNativeQuery("INSERT INTO AUTHORITIES (USERNAME, AUTHORITY) VALUES (:username , :authority);");
            logger.info(query.toString());
            query.setParameter("username", username);
            query.setParameter("authority", "ROLE_USER");
            query.executeUpdate();
            em.getTransaction().commit();

        } finally {
            JpaUtil.closeQuietly(em);
        }
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        em = null;

        try {
            em = JpaUtil.getFactory().createEntityManager();
            em.getTransaction().begin();
            em.merge(user);
            em.getTransaction().commit();
        } finally {
            JpaUtil.closeQuietly(em);
        }
    }

}
