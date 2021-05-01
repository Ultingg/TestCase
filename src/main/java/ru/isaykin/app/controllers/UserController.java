package ru.isaykin.app.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.isaykin.app.exceptions.UserIsAlreadyExistException;
import ru.isaykin.app.exceptions.UserNotFoundException;
import ru.isaykin.app.model.User;
import ru.isaykin.app.services.UserService;

import javax.validation.Valid;
import java.util.Map;
import java.util.NoSuchElementException;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestController
@RequestMapping
public class UserController {


    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        ResponseEntity<User> result;
        try {
            User user = userService.getUserById(id);
            result = new ResponseEntity<>(user, OK);
        } catch (UserNotFoundException exception) {
            log.info("Incorrect id of person");
            result = new ResponseEntity<>(NOT_FOUND);
        }
        return result;
    }


    @PostMapping("users")
    public ResponseEntity<Long> addNewUser(@Valid @RequestBody User user) {
        ResponseEntity<Long> result;
        Long personId = userService.addUser(user);
        if (personId == null) {
            result = new ResponseEntity<>(BAD_REQUEST);
        } else {
            result = new ResponseEntity<>(personId, CREATED);
        }
        return result;
    }


    @PatchMapping("users/{id}")
    public ResponseEntity<Map> updateStatus(@PathVariable Long id,
                                            @RequestParam String status) {
        ResponseEntity<Map> result;
        Map<String, Object> responseMap = userService.updateStatus(id, status);
        result = new ResponseEntity<>(responseMap, OK);
        return result;
    }


}
