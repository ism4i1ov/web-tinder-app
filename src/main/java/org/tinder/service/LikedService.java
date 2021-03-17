package org.tinder.service;

import org.tinder.dao.impl.LikeDao;
import org.tinder.entity.User;

import java.util.*;

public class LikedService {

    private final LikeDao likeDao;

    public LikedService(LikeDao likeDao) {
        this.likeDao = likeDao;
    }

    public List<User> getLikedUsersById(String id) {
        return likeDao.getLikedUsersByUserId(id);
    }
}
