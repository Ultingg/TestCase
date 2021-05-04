package ru.isaykin.app.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Component
public class UserDTO {

    private Long id;

    @Size(min = 1, max = 80, message = "Incorrect name. Min size = 1, Max size = 80")
    @Pattern(regexp = "[\\s?[a-zA-Zа-яА-я]*\\s?]{1,80}", message = "Incorrect name. Use only letters.")
    private String name;

    @Pattern(regexp = "(\\+)[0-9]{11,13}", message = "Incorrect phone number. Use this format +70123456789 or +7012345678910.")
    private String phoneNumber;

    @Email(message = "Incorrect email. Please enter email in that form: myemailname@email.ru")
    private String email;

    @Pattern(regexp = "(Offline|Online|Away)", message = "Incorrect status. Use this format Offline, Online, Away.")
    private String status;
}
