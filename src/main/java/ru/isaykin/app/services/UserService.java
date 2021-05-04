package ru.isaykin.app.services;

import ru.isaykin.app.dto.UserDTO;

import java.util.Map;

public interface UserService {

    Long addUser(UserDTO userDTO);

    UserDTO getUserById(Long id);

    Map<String, Object> updateStatus(Long id, UserDTO userDTO);
}

