package com.csed.Mail.Services;
import com.csed.Mail.mappers.impl.FolderMapperImpl;
import com.csed.Mail.model.Dtos.FolderDto;
import com.csed.Mail.model.FolderEntity;
import com.csed.Mail.model.UserEntity;
import com.csed.Mail.repositories.FolderRepository;
import com.csed.Mail.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
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
    public List<FolderDto> showfolders(Long userId) {
        List<FolderEntity> allfolders = folderRepository.findByOwnerId(userId);
        List<FolderDto> allFolderDto = new ArrayList<>();
        for(FolderEntity f : allfolders){
            allFolderDto.add(f.getDto());
        }
        if (allfolders.isEmpty())
            throw new IllegalArgumentException("THIS USER HAS NO FOLDERS");
        return allFolderDto;
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
   }
public FolderDto renaming(FolderDto folderDto){
    List<String> defaultfolder = Arrays.asList("Inbox", "Sent", "Drafts", "Trash");
    Optional<FolderEntity> defaultfol = folderRepository.getFolderById(folderDto.getId());
    if (defaultfolder.contains(defaultfol.get().getName())) {
        throw new IllegalArgumentException("This folder can't be renamed");
    }
    Optional<FolderEntity> oldfolder=folderRepository.getFolderByName(folderDto.getName());
    if(oldfolder.isPresent()&&oldfolder.get().getUserId().equals(folderDto.getUserId()))
        throw new IllegalArgumentException("this folder name is used");
    Optional<FolderEntity> existedfolder= folderRepository.getFolderById(folderDto.getId());

    FolderEntity folderEntity = existedfolder.get();
    folderEntity.setName(folderDto.getName());
    folderEntity.setOwner(userRepository.findById(folderDto.getUserId()).get());

    FolderEntity editedFolder = folderRepository.save(folderEntity);
    return editedFolder.getDto();
}
public void delete(Long id ) {
        List<String> defaultfolder = Arrays.asList("Inbox", "Sent", "Drafts", "Trash");
        Optional<FolderEntity> existedfolder = folderRepository.getFolderById(id);
        if (existedfolder.isPresent()) {
            if (defaultfolder.contains(existedfolder.get().getName())) {
                throw new IllegalArgumentException("This folder can't be deleted");
            }
            folderRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Folder not found for the given id and userId");
        }
    }
}


