package org.tinder.service;

import org.tinder.controller.SignUpServlet;
import org.tinder.dao.impl.UserDao;
import org.tinder.entity.User;

import java.util.Optional;

public class SignUpService {

    private final UserDao userDao;

    public SignUpService(UserDao userDao) {
        this.userDao = userDao;
    }

    public Optional<User> findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    public void createNewUser(User user) {
        userDao.create(user);
    }
}
