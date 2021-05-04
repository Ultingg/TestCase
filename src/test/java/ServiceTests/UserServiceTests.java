package ServiceTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.isaykin.app.dto.UserDTO;
import ru.isaykin.app.exceptions.IncorrectStatusException;
import ru.isaykin.app.exceptions.UserIsAlreadyExistException;
import ru.isaykin.app.exceptions.UserNotFoundException;
import ru.isaykin.app.model.User;
import ru.isaykin.app.repository.UserRepository;
import ru.isaykin.app.services.UserService;
import ru.isaykin.app.services.UserServiceImp;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

import static java.util.Optional.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class UserServiceTests {

    private UserService userService;
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        userRepository = mock(UserRepository.class);
        userService = new UserServiceImp(userRepository);
    }

    @Test
    public void addUser_valid_UserDTO() {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("Leo");
        userDTO.setEmail("leoleo@mail.ru");
        userDTO.setPhoneNumber("+79874561254");
        userDTO.setStatus("Online");
        User expectedUser = new User();
        expectedUser.setId(1L);
        expectedUser.setName("Leo");
        expectedUser.setEmail("leoleo@mail.ru");
        expectedUser.setPhoneNumber("+79874561254");
        expectedUser.setStatus("Online");
        when(userRepository.save(any(User.class))).thenReturn(expectedUser);

        Long actual = userService.addUser(userDTO);

        assertEquals(1L, actual);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void addUser_invalidUserId_UserIsAlreadyExistException() {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);

        assertThrows(UserIsAlreadyExistException.class, () -> userService.addUser(userDTO));
        verify(userRepository, times(0)).save(any(User.class));
    }

    @Test
    public void getUserById_valid_UserDTO(){
        UserDTO expectedUserDTO = new UserDTO();
        expectedUserDTO.setId(1L);
        expectedUserDTO.setName("Leo");
        expectedUserDTO.setEmail("leoleo@mail.ru");
        expectedUserDTO.setPhoneNumber("+79874561254");
        expectedUserDTO.setStatus("Online");
        User userFromDB = new User();
        userFromDB.setId(1L);
        userFromDB.setName("Leo");
        userFromDB.setEmail("leoleo@mail.ru");
        userFromDB.setPhoneNumber("+79874561254");
        userFromDB.setStatus("Online");
        userFromDB.setOnlineTimestamp(LocalDateTime.now());
        when(userRepository.findById(1L)).thenReturn(of(userFromDB));

        UserDTO actual = userService.getUserById(1L);

        assertEquals(expectedUserDTO, actual);
        verify(userRepository,times(1)).findById(1L);
        verify(userRepository,times(1)).findById(anyLong());
    }
    @Test
    public void getUserById_expiredStatusValid_UserDTO(){
        UserDTO expectedUserDTO = new UserDTO();
        expectedUserDTO.setId(1L);
        expectedUserDTO.setName("Leo");
        expectedUserDTO.setEmail("leoleo@mail.ru");
        expectedUserDTO.setPhoneNumber("+79874561254");
        expectedUserDTO.setStatus("Away");
        User userFromDB = new User();
        userFromDB.setId(1L);
        userFromDB.setName("Leo");
        userFromDB.setEmail("leoleo@mail.ru");
        userFromDB.setPhoneNumber("+79874561254");
        userFromDB.setStatus("Online");
        userFromDB.setOnlineTimestamp(LocalDateTime.now().minus(6,ChronoUnit.MINUTES));
        when(userRepository.findById(1L)).thenReturn(of(userFromDB));

        UserDTO actual = userService.getUserById(1L);

        assertEquals(expectedUserDTO, actual);
        verify(userRepository,times(1)).findById(1L);
        verify(userRepository,times(1)).findById(anyLong());
    }
    @Test
    public void getUserById_validUserId_UserDTO(){
        assertThrows(UserNotFoundException.class, () -> userService.getUserById(1L));

        verify(userRepository,times(1)).findById(1L);
        verify(userRepository,times(1)).findById(anyLong());
    }


    @Test
    public void updateStatus_valid_UserDTO() {
        UserDTO newUserDTO = new UserDTO();
        newUserDTO.setId(1L);
        newUserDTO.setName("Leo");
        newUserDTO.setEmail("leoleo@mail.ru");
        newUserDTO.setPhoneNumber("+79874561254");
        newUserDTO.setStatus("Online");
        User userFromDB = new User();
        userFromDB.setId(1L);
        userFromDB.setName("Leo");
        userFromDB.setEmail("leoleo@mail.ru");
        userFromDB.setPhoneNumber("+79874561254");
        userFromDB.setStatus("Offline");
        Map<String, Object> expectedMap = new HashMap<>();
        expectedMap.put("oldStatus", "Offline");
        expectedMap.put("newStatus", "Online");
        expectedMap.put("personId", 1L);
        when(userRepository.findById(1L)).thenReturn(of(userFromDB));

        Map<String, Object> actual = userService.updateStatus(1L, newUserDTO);

        assertEquals(expectedMap, actual);
        verify(userRepository,times(1)).findById(1L);
        verify(userRepository,times(1)).findById(anyLong());
        verify(userRepository,times(1)).save(any(User.class));
    }
    @Test
    public void updateStatus_invalidUserId_UserNotFoundException() {
        assertThrows(UserNotFoundException.class,()->userService.updateStatus(1L, new UserDTO()));

        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).findById(anyLong());
    }
    @Test
    public void updateStatus_invalidStatus_IncorrectStatusException() {
        UserDTO newUserDTO = new UserDTO();
        newUserDTO.setId(1L);
        newUserDTO.setName("Leo");
        newUserDTO.setEmail("leoleo@mail.ru");
        newUserDTO.setPhoneNumber("+79874561254");
        newUserDTO.setStatus("Onine");
        when(userRepository.findById(1L)).thenReturn(of(new User()));
        assertThrows(IncorrectStatusException.class,()->userService.updateStatus(1L, newUserDTO));

        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).findById(anyLong());
    }
}
