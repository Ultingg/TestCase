package ru.isaykin.app.services;

import org.springframework.stereotype.Service;
import ru.isaykin.app.exceptions.IncorrectStatusException;
import ru.isaykin.app.exceptions.UserIsAlreadyExistException;
import ru.isaykin.app.exceptions.UserNotFoundException;
import ru.isaykin.app.model.User;
import ru.isaykin.app.repository.UserRepository;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;

    public UserServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Long addUser(User user) {
        Long id = user.getId();
        if (id != null)
            throw new UserIsAlreadyExistException("This id = " + id + " is occupied. Delete id from you request to add new user.");
        User user1 = userRepository.save(user);
        return user1.getId();
    }

    public User getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Wrong id. User not found."));
        return user;
    }

    public Map<String, Object> updateStatus(Long id, String newStatus) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Wrong id. Person not found."));
        Map<String, Object> responseMap = new HashMap<>();
        if (!statusValidation(newStatus))
            throw new IncorrectStatusException("Incorrect status. Use this format Offline, Online, Away.");
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

}
