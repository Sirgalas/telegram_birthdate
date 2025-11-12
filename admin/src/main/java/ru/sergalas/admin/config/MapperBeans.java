package ru.sergalas.admin.config;

import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.sergalas.admin.mapper.ParticipantMapper;
import ru.sergalas.admin.mapper.PeriodicityMapper;
import ru.sergalas.admin.mapper.UserMapper;

@Configuration
public class MapperBeans {
    @Bean
    UserMapper userMapper() {return Mappers.getMapper(UserMapper.class);}

    @Bean
    PeriodicityMapper periodicityMapper() {return Mappers.getMapper(PeriodicityMapper.class);}

    @Bean
    ParticipantMapper participantMapper() {return Mappers.getMapper(ParticipantMapper.class);}
}
