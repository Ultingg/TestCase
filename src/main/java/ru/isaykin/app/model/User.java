package ru.isaykin.app.model;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Table(value = "users")
public class User {

    @Id
    private Long id;
    private String name;
    private String phoneNumber;
    private String email;
    private String status;
    private LocalDateTime onlineTimestamp;

    public User() {
    }

}
