package org.tinder.dao.impl;

import org.tinder.dao.Database;
import org.tinder.entity.Like;
import org.tinder.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LikeDao implements Database<Like> {

    @Override
    public int create(Like like) {
        String sql = "insert into \"like\" values(default,?,?) RETURNING id";
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setBoolean(1, like.isOperationType());
            preparedStatement.setInt(2, (int) like.getLikedUserId());
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt("id");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }

    @Override
    public boolean delete(String id) {
        String sql = "delete from \"like\" where id = ?";
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, Integer.parseInt(id));
            return preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Like like) {
        return false;
    }

    @Override
    public List<Like> findAll() {
        List<Like> likeList = new ArrayList<>();
        String sql = "select * from \"like\"";
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int likerUserId = resultSet.getInt("liker_user_id");
                int likedUserId = resultSet.getInt("liked_user_id");
//                likeList.add();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return likeList;
    }

    @Override
    public Optional<Like> findById(String id) {
        String sql = "select * from \"like\" where id = ?";
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, Integer.parseInt(id));
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int likeId = resultSet.getInt("id");
            int likedUserId = resultSet.getInt("liked_user_id");
            boolean operationType = resultSet.getBoolean("operation_type");
            return Optional.of(new Like(likeId, likedUserId, operationType));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Optional.empty();
    }

    public List<User> getLikedUsersByUserId(String id) {
        List<User> userList = new ArrayList<>();
        String sql = "select u.* from liked l left join users u on u.id = l.liked_user_id where l.liker_user_id = ?";
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, Integer.parseInt(id));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int userId = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                String avatarLink = resultSet.getString("avatar_link");
                String profession = resultSet.getString("profession");
                LocalDateTime lastLogin = resultSet.getTimestamp("last_login").toLocalDateTime();
                userList.add(new User(userId, name, surname, avatarLink, profession, lastLogin));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return userList;

    }

    public Optional<User> findUserForLike(String userId) {
        String sql = "select u.* from users u left join liked l on ? <> l.liker_user_id where u.id <> ?";
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, Integer.parseInt(userId));
            preparedStatement.setInt(2, Integer.parseInt(userId));
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String username = resultSet.getString("username");
                String avatarLink = resultSet.getString("avatar_link");
                return Optional.of(new User(id, username, avatarLink));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Optional.empty();
    }

    public int createUserLiker(int likerUserId, int likeId) {
        String sql = "insert into liked values(?,?,default) returning id";
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, likerUserId);
            preparedStatement.setInt(2, likeId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt("id");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }
}
