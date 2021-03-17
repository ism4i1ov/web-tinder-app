package org.tinder.entity;

public class Like {
    private final long id;
    private final long likedUserId;
    private final boolean operationType;

    public Like(long id, long likedUserId, boolean operationType) {
        this.id = id;
        this.likedUserId = likedUserId;
        this.operationType = operationType;
    }

    public long getId() {
        return id;
    }

    public long getLikedUserId() {
        return likedUserId;
    }

    public boolean isOperationType() {
        return operationType;
    }

}
