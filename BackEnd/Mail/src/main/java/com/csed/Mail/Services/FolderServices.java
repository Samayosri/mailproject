package com.csed.Mail.Services;
import com.csed.Mail.mappers.impl.FolderMapperImpl;
import com.csed.Mail.model.Dtos.FolderDto;
import com.csed.Mail.model.FolderEntity;
import com.csed.Mail.repositories.FolderRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FolderServices {
    private final FolderRepository folderRepository;
    private final FolderMapperImpl folderMapper;
    public FolderServices(FolderRepository folderRepository,FolderMapperImpl folderMapper) {
        this.folderRepository = folderRepository;
        this.folderMapper=folderMapper;
    }
    public void create(FolderDto folderDto) throws IllegalArgumentException{
        Optional<FolderEntity> existedfolder=folderRepository.getFolderByName(folderDto.getName());
        if(existedfolder.isPresent()){
            throw new IllegalArgumentException("this folder name is used");
        }
//        Optional<FolderEntity> existeduser=folderRepository.getFolderByOwnerId(folderDto.getOwnerId());
//        if(existeduser.isEmpty())
//            throw new IllegalArgumentException("this folder name is used");

//        folderDto.setId(null);
//        FolderEntity folderadded=folderMapper.mapFromDto(folderDto);
//        folderRepository.save();
//        return folderMapper.mapToDto(newfolder);
        return ;
    }
}
