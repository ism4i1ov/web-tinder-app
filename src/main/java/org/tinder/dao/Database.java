package org.tinder.dao;

import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public interface Database<T> {

    default Connection getConnection() throws SQLException {
        String url = System.getenv("Database_URL");
        String username = System.getenv("Database_username");
        String password = System.getenv("Database_password");
        return DriverManager.getConnection(url, username, password);

    }

    int create(T t);

    boolean delete(String id);

    boolean update(T t);

    List<T> findAll();

    Optional<T> findById(String id);
}
