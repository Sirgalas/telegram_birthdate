package ru.sergalas.data.entities.user.mapper;

import org.mapstruct.Mapper;
import ru.sergalas.data.entities.user.data.UserRequestData;
import ru.sergalas.data.entities.user.data.UserResponseData;
import ru.sergalas.data.entities.user.entity.User;

@Mapper
public interface UserMapper {
    UserResponseData toData(final User user);
    User toEntity(final UserRequestData data);
}
