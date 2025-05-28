package ru.sergalas.data.config;

import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.sergalas.data.entities.user.mapper.UserMapper;

@Configuration
public class MapperBean {
    @Bean
    UserMapper userMapper() {return Mappers.getMapper(UserMapper.class);}
}
