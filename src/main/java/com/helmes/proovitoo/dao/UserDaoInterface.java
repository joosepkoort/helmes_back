package com.helmes.proovitoo.dao;

import com.helmes.proovitoo.model.User;
import org.springframework.transaction.annotation.Transactional;

public interface UserDaoInterface {
    User findUserByUserName(String username);

    @Transactional
    void addRole(User user);

    @Transactional
    void saveUser(User user);
}
