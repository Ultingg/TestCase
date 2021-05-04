package ru.isaykin.app.services;

import org.springframework.stereotype.Service;
import ru.isaykin.app.dto.UserDTO;
import ru.isaykin.app.exceptions.IncorrectStatusException;
import ru.isaykin.app.exceptions.UserIsAlreadyExistException;
import ru.isaykin.app.exceptions.UserNotFoundException;
import ru.isaykin.app.mappers.UserMapper;
import ru.isaykin.app.model.User;
import ru.isaykin.app.repository.UserRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;

    public UserServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Long addUser(UserDTO userDTO) {
        Long id = userDTO.getId();
        if (id != null) {
            throw new UserIsAlreadyExistException("This id = " + id + " is occupied. Delete id from you request to add new user.");
        }
        User user = UserMapper.INSTANCE.fromUserDTOToUser(userDTO);
        user.setOnlineTimestamp(LocalDateTime.now());
        user = userRepository.save(user);
        return user.getId();
    }

    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Wrong id. User not found."));
        UserDTO userDTO = UserMapper.INSTANCE.fromUserToUserDTO(user);
        if (checkIfOnlineStatusExpired(user) && user.getStatus().equals("Online")) {
            changingExpiredStatus(user, userDTO);
        }
        return userDTO;
    }

    private boolean checkIfOnlineStatusExpired(User user) {
        LocalDateTime timeLimit = user.getOnlineTimestamp().plus(5, ChronoUnit.MINUTES);
        LocalDateTime now = LocalDateTime.now();
        return timeLimit.isBefore(now);
    }

    private void changingExpiredStatus(User user, UserDTO userDTO) {
        userDTO.setStatus("Away");
        user.setStatus("Away");
        userRepository.save(user);
    }

    public Map<String, Object> updateStatus(Long id, UserDTO userDTO) {
        String newStatus = userDTO.getStatus();
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Wrong id. Person not found."));
        if (!statusValidation(newStatus))
            throw new IncorrectStatusException("Incorrect status. Use this format Offline, Online, Away.");
        if (newStatus.equals("Online")) updateOnlineTimestamp(user);
        Map<String, Object> responseMap = new HashMap<>();

        responseMap.put("oldStatus", user.getStatus());
        responseMap.put("newStatus", newStatus);
        responseMap.put("personId", id);
        user.setStatus(newStatus);
        userRepository.save(user);
        return responseMap;
    }

    private boolean statusValidation(String status) {
        return status.matches("(Offline|Online|Away)");
    }

    private void updateOnlineTimestamp(User user) {
        user.setOnlineTimestamp(LocalDateTime.now());
    }


}
