package com.csed.Mail.mappers.impl;

import com.csed.Mail.mappers.Mapper;
import com.csed.Mail.model.Dtos.MailDto;
import com.csed.Mail.model.MailEntity;
import org.modelmapper.ModelMapper;

public class MailMapperImpl implements Mapper<MailEntity, MailDto> {
    private final ModelMapper modelMapper;

    public MailMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    @Override
    public MailDto mapToDto(MailEntity mailEntity) {
        return this.modelMapper.map(mailEntity, MailDto.class);
    }

    @Override
    public MailEntity mapFromDto(MailDto mailDto) {
        return this.modelMapper.map(mailDto, MailEntity.class);
    }
}
