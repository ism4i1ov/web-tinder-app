package org.tinder.service;

import org.tinder.dao.impl.UserDao;
import org.tinder.entity.User;

import java.time.LocalDateTime;
import java.util.Optional;

public class SignInService {

    private final UserDao userDao;

    public SignInService(UserDao userDao) {
        this.userDao = userDao;
    }

    public Optional<User> getUserByUsernameAndPassword(String username, String password) {
        return userDao.findByUsernameAndPassword(username, password);
    }

    public void updateLastLogin(String userId, LocalDateTime lastLogin) {
        userDao.updateLastLogin(userId, lastLogin);
    }
}
