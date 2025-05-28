package ru.sergalas.data.entities.user.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import ru.sergalas.data.entities.user.data.UserRequestData;
import ru.sergalas.data.entities.user.data.UserResponseData;
import ru.sergalas.data.entities.user.entity.User;
import ru.sergalas.data.entities.user.exception.UserNotFoundException;
import ru.sergalas.data.entities.user.mapper.UserMapper;
import ru.sergalas.data.entities.user.repository.UserRepository;
import ru.sergalas.data.entities.user.service.UserService;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponseData getUserByUserName(String username) {
        return userRepository
            .findByUsername(username)
            .map(userMapper::toData)
            .orElseThrow(() -> new UserNotFoundException("{user.not_found.error}"));
    }

    @Override
    public UserResponseData createUser(UserRequestData data) throws BindException {

        User user = userMapper.toEntity(data);
        return userMapper.toData(userRepository.save(user));
    }
}
