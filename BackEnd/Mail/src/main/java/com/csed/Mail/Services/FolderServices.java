package com.csed.Mail.Services;
import com.csed.Mail.mappers.impl.FolderMapperImpl;
import com.csed.Mail.model.Dtos.FolderDto;
import com.csed.Mail.model.FolderEntity;
import com.csed.Mail.model.UserEntity;
import com.csed.Mail.repositories.FolderRepository;
import com.csed.Mail.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FolderServices {
    private final FolderRepository folderRepository;
    private final UserRepository userRepository;
    public FolderServices(FolderRepository folderRepository,UserRepository userRepository) {
        this.folderRepository = folderRepository;
        this.userRepository=userRepository;
    }
    public List<FolderEntity> showfolders(Long userId) {
        List<FolderEntity> allfolders = folderRepository.findByOwnerId(userId);
        if (allfolders.isEmpty())
            throw new IllegalArgumentException("THIS USER HAS NO FOLDERS");
        return allfolders;
    }
  public FolderDto create(FolderDto folderDto) throws IllegalArgumentException{
      Optional<FolderEntity> existedfolder=folderRepository.getFolderByName(folderDto.getName());
      if(existedfolder.isPresent()&&existedfolder.get().getUserId().equals(folderDto.getUserId()))
           throw new IllegalArgumentException("this folder name is used");
     folderDto.setId(null);
      FolderEntity folderadded=folderDto.getfolder();
      Optional<UserEntity> existeduser=userRepository.findById(folderDto.getUserId());
      folderadded.setOwner(existeduser.get());
   folderRepository.save(folderadded);
     return folderadded.getDto();
   }}

