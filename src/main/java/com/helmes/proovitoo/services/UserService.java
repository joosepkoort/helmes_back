package com.helmes.proovitoo.services;

import com.helmes.proovitoo.dao.UserDao;
import com.helmes.proovitoo.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import javax.persistence.NoResultException;

@Service
public class UserService implements UserServiceInterface {

    UserDao dao = new UserDao();

    /**
     * Registers a new user. Mandatory fields are username and password
     * @param user
     * @return
     */
    @Override
    public Boolean registerUser(User user) {
        boolean exists;
        try {
            dao.findUserByUserName(user.getUserName());
            exists = true;
        } catch (NoResultException nre) {
            exists = false;
        }
        if (exists) {
            return false;
        }

        User newUser = new User();
        newUser.setUserName(user.getUserName());

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
        String password = encoder.encode(user.getPassword());
        newUser.setPassword(password);
        newUser.setName(user.getUserName());
        newUser.setEnabled(true);
        dao.saveUser(newUser);
        dao.addRole(newUser);
        return true;
    }
}
