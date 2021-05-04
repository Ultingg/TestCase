import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.isaykin.app.dto.UserDTO;
import ru.isaykin.app.model.User;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ValidationTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void namePerson_validConstrain() {
        UserDTO user = new UserDTO();
        user.setName("Jhon");
        Set<ConstraintViolation<UserDTO>> violations = validator.validate(user);

        assertTrue(violations.isEmpty());
    }
    @Test
    public void namePerson_invalidConstrain() {
        UserDTO user = new UserDTO();
        user.setName("Jhon0-");
        Set<ConstraintViolation<UserDTO>> violations = validator.validate(user);

        assertFalse(violations.isEmpty());
    }
    @Test
    public void phoneNumberPerson_invalidConstrain() {
        UserDTO user = new UserDTO();
        user.setPhoneNumber("+70123456");
        Set<ConstraintViolation<UserDTO>> violations = validator.validate(user);

        assertFalse(violations.isEmpty());
    }
    @Test
    public void phoneNumberPerson_validConstrain() {
        UserDTO user = new UserDTO();
        user.setPhoneNumber("+70123456789");
        Set<ConstraintViolation<UserDTO>> violations = validator.validate(user);

        assertTrue(violations.isEmpty());
    }
    @Test
    public void emailPerson_validConstrain() {
        UserDTO user = new UserDTO();
        user.setEmail("goodmail@yandex.ru");
        Set<ConstraintViolation<UserDTO>> violations = validator.validate(user);

        assertTrue(violations.isEmpty());
    }
    @Test
    public void emailPerson_invalidConstrain() {
        UserDTO user = new UserDTO();
        user.setEmail("goodmailyandex.ru");
        Set<ConstraintViolation<UserDTO>> violations = validator.validate(user);

        assertFalse(violations.isEmpty());
    }
    @Test
    public void statusPerson_invalidConstrain() {
        UserDTO user = new UserDTO();
        user.setEmail("Ebay");
        Set<ConstraintViolation<UserDTO>> violations = validator.validate(user);

        assertFalse(violations.isEmpty());
    }
    @Test
    public void statusPerson_validConstrain() {
        UserDTO user = new UserDTO();
        user.setStatus("Away");
        Set<ConstraintViolation<UserDTO>> violations = validator.validate(user);

        assertTrue(violations.isEmpty());
    }

}
