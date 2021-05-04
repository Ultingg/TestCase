package ru.isaykin.app.mappers;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import ru.isaykin.app.dto.UserDTO;
import ru.isaykin.app.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mappings({
            @Mapping(source = "user.id", target = "id"),
            @Mapping(source = "user.name", target = "name"),
            @Mapping(source = "user.phoneNumber", target = "phoneNumber"),
            @Mapping(source = "user.email", target = "email"),
            @Mapping(source = "user.status", target = "status")})
    UserDTO fromUserToUserDTO(User user);

    @Mappings({
            @Mapping(source = "userDTO.id", target = "id"),
            @Mapping(source = "userDTO.name", target = "name"),
            @Mapping(source = "userDTO.phoneNumber", target = "phoneNumber"),
            @Mapping(source = "userDTO.email", target = "email"),
            @Mapping(source = "userDTO.status", target = "status")})
    User fromUserDTOToUser(UserDTO userDTO);


}
