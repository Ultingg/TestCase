package ru.isaykin.app.services;

import ru.isaykin.app.model.User;

import java.util.Map;

public interface UserService {

    public Long addUser(User user);

    public User getUserById(Long id);

    public Map<String, Object> updateStatus(Long id, String newStatus);
}

