package mappersTests;

import org.junit.jupiter.api.Test;
import ru.isaykin.app.DTO.UserDTO;
import ru.isaykin.app.mappers.UserMapper;
import ru.isaykin.app.model.User;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserMappingTests {



    @Test
    public void givenUserEntityToUserDTO() {
         UserDTO userDTO = new UserDTO();
         userDTO.setId(1l);
         userDTO.setName("Petr");
         userDTO.setEmail("petra@mail.ru");
         userDTO.setPhoneNumber("+79212145698");

         User user = UserMapper.INSTANCE.fromUserDTOToUser(userDTO);

         assertEquals(1l,user.getId());
         assertEquals("Petr",user.getName());
         assertEquals("petra@mail.ru",user.getEmail());
         assertEquals("+79212145698",user.getPhoneNumber());
     }
    @Test
    public void givenUserDTOToUserEntity() {
        User user = new User();
        user.setId(1l);
        user.setName("Petr");
        user.setEmail("petra@mail.ru");
        user.setPhoneNumber("+79212145698");

        UserDTO userDTO = UserMapper.INSTANCE.fromUserToUserDTO(user);

        assertEquals(1l,userDTO.getId());
        assertEquals("Petr",userDTO.getName());
        assertEquals("petra@mail.ru",userDTO.getEmail());
        assertEquals("+79212145698",userDTO.getPhoneNumber());
    }

}
