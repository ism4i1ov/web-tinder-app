package org.tinder.service;

import org.tinder.dao.impl.LikeDao;
import org.tinder.entity.Like;
import org.tinder.entity.User;

import java.util.*;

public class LikeService {

    private final LikeDao likeDao;

    public LikeService(LikeDao likeDao) {
        this.likeDao = likeDao;
    }

    public Optional<User> getUserForLike(String id) {
        return likeDao.findUserForLike(id);
    }

    public void likedUser(Like like, String likerUserId) {
        int likeId = likeDao.create(like);
        likeDao.createUserLiker(Integer.parseInt(likerUserId), likeId);
    }
}
