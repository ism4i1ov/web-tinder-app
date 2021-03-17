package org.tinder.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class User {
    private final long id;
    private String name;
    private String surname;
    private String username;
    private String password;
    private String avatarLink;
    private LocalDateTime lastLogin;
    private String profession;

    public User(long id) {
        this.id = id;
    }

    public User(long id, String name, String surname, String username, String password, String avatarLink, String profession) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.password = password;
        this.avatarLink = avatarLink;
        this.profession = profession;
    }

    public User(long id, String username, String avatarLink) {
        this.id = id;
        this.username = username;
        this.avatarLink = avatarLink;
    }


    public User(long id, String name, String surname, String avatarLink, String profession, LocalDateTime lastLogin) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.avatarLink = avatarLink;
        this.profession = profession;
        this.lastLogin = lastLogin;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatarLink() {
        return avatarLink;
    }

    public void setAvatarLink(String avatarLink) {
        this.avatarLink = avatarLink;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getLastLoginString() {
        return lastLogin.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    @Override
    public String toString() {
        return String.format("User{id=%d, name='%s', surname='%s', username='%s', password='%s', avatarLink='%s'}", id, name, surname, username, password, avatarLink);
    }
}
