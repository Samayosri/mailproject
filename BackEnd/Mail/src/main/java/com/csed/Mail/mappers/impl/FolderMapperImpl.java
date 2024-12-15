package com.csed.Mail.mappers.impl;

import com.csed.Mail.mappers.Mapper;
import com.csed.Mail.model.Dtos.FolderDto;
import com.csed.Mail.model.FolderEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FolderMapperImpl implements Mapper<FolderEntity, FolderDto> {
    private final ModelMapper modelMapper;

    public FolderMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public FolderDto mapToDto(FolderEntity folderEntity) {
        return this.modelMapper.map(folderEntity, FolderDto.class);
    }

    @Override
    public FolderEntity mapFromDto(FolderDto folderDto) {
        return this.modelMapper.map(folderDto, FolderEntity.class);
    }

    @Override
    public List<FolderDto> mapListToDto(List<FolderEntity> a) {
        return List.of();
    }

    @Override
    public List<FolderEntity> mapListFromDto(List<FolderDto> b) {
        return List.of();
    }
}
