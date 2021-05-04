package ru.isaykin.app.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.isaykin.app.dto.UserDTO;
import ru.isaykin.app.services.UserService;

import javax.validation.Valid;
import java.util.Map;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Slf4j
@RestController
@RequestMapping
public class UserController {


    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("users/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {

        ResponseEntity<UserDTO> result;
        UserDTO userDTO = userService.getUserById(id);
        result = new ResponseEntity<>(userDTO, OK);
        return result;
    }

    @PostMapping("users")
    public ResponseEntity<Long> addNewUser(@Valid @RequestBody UserDTO userDTO) {
        ResponseEntity<Long> result;
        Long personId = userService.addUser(userDTO);
        result = new ResponseEntity<>(personId, CREATED);
        return result;
    }

    @PutMapping("users/{id}")
    public ResponseEntity<Map<String, Object>> updateStatus(@PathVariable Long id,
                                            @Valid @RequestBody UserDTO userDTO) {
        ResponseEntity<Map<String, Object>> result;
        Map<String, Object> responseMap = userService.updateStatus(id, userDTO);
        result = new ResponseEntity<>(responseMap, OK);
        return result;
    }


}
