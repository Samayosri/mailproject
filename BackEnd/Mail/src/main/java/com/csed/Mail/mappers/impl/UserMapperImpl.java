package com.csed.Mail.mappers.impl;

import com.csed.Mail.mappers.Mapper;
import com.csed.Mail.model.Dtos.UserDto;
import com.csed.Mail.model.UserEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapperImpl implements Mapper<UserEntity, UserDto> {
    private final ModelMapper modelMapper;

    public UserMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDto mapToDto(UserEntity userEntity) {
        return this.modelMapper.map(userEntity, UserDto.class);
    }

    @Override
    public UserEntity mapFromDto(UserDto userDto) {
        return this.modelMapper.map(userDto, UserEntity.class);
    }

    @Override
    public List<UserDto> mapListToDto(List<UserEntity> a) {
        return List.of();
    }

    @Override
    public List<UserEntity> mapListFromDto(List<UserDto> b) {
        return List.of();
    }
}
