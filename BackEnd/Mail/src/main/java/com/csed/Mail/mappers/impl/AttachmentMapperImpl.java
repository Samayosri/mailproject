package com.csed.Mail.mappers.impl;

import com.csed.Mail.mappers.Mapper;
import com.csed.Mail.model.AttachmentEntity;
import com.csed.Mail.model.Dtos.AttachmentDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AttachmentMapperImpl implements Mapper<AttachmentEntity, AttachmentDto> {

    private final ModelMapper modelMapper;

    public AttachmentMapperImpl(ModelMapper modelMapper)
    {
        this.modelMapper = modelMapper;
    }

    @Override
    public AttachmentDto mapToDto(AttachmentEntity attachmentEntity) {
        return this.modelMapper.map(attachmentEntity, AttachmentDto.class);
    }

    @Override
    public AttachmentEntity mapFromDto(AttachmentDto attachmentDto) {
        return this.modelMapper.map(attachmentDto, AttachmentEntity.class);
    }
}
