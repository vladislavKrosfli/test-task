package com.example.testTask.service;

public interface KeycloakService {

    void addUser(String username, String password, String role);

    void deleteUserByUsername(String username);

    void deleteAllUsers();
}
