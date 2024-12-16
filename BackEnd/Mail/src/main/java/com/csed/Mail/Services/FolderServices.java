package com.csed.Mail.Services;
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
    public List<FolderDto> getFolders(Long userId) {
        List<FolderEntity> allFolders = folderRepository.findByOwnerId(userId);
        List<FolderDto> allFolderDto = new ArrayList<>();
        for(FolderEntity f : allFolders){
            allFolderDto.add(f.getDto());
        }
        if (allFolders.isEmpty())
            throw new IllegalArgumentException("THIS USER HAS NO FOLDERS");
        return allFolderDto;
    }
  public FolderDto create(FolderDto folderDto) throws IllegalArgumentException{
      Optional<FolderEntity> existedFolder=folderRepository.getFolderByName(folderDto.getName());
      if(existedFolder.isPresent()&&existedFolder.get().getUserId().equals(folderDto.getUserId()))
           throw new IllegalArgumentException("this folder name is used");
      folderDto.setId(null);
      FolderEntity folderAdded=folderDto.getfolder();
      Optional<UserEntity> existedUser=userRepository.findById(folderDto.getUserId());
      folderAdded.setOwner(existedUser.get());
      folderRepository.save(folderAdded);
     return folderAdded.getDto();
   }
public FolderDto renaming(FolderDto folderDto){
    List<String> defaultFolder = Arrays.asList("Inbox", "Sent", "Drafts", "Trash");
    Optional<FolderEntity> folder = folderRepository.findById(folderDto.getId());
    if(folder.isPresent()){
        if (defaultFolder.contains(folder.get().getName())) {
            throw new IllegalArgumentException("This folder can't be renamed");
        }
        Optional<FolderEntity> oldFolder=folderRepository.getFolderByName(folderDto.getName());
        if(oldFolder.isPresent()&&oldFolder.get().getUserId().equals(folderDto.getUserId()))
            throw new IllegalArgumentException("this folder name is used");
        FolderEntity folderEntity = folder.get();
        folderEntity.setName(folderDto.getName());
        FolderEntity editedFolder = folderRepository.save(folderEntity);
        return editedFolder.getDto();

    }
    else{
        throw new IllegalArgumentException("Folder not found");
    }

}
public void delete(Long id ) {

        List<String> defaultFolder = Arrays.asList("Inbox", "Sent", "Drafts", "Trash");
        Optional<FolderEntity> existedFolder = folderRepository.findById(id);
        if (existedFolder.isPresent()) {
            if (defaultFolder.contains(existedFolder.get().getName())) {
                throw new IllegalArgumentException("This folder can't be deleted");
            }
            folderRepository.deleteById(id);
            UserEntity user = existedFolder.get().getOwner();
            user.getFolders().remove(existedFolder.get());
            userRepository.save(user);

        } else {
            throw new IllegalArgumentException("Folder not found for the given id and userId");
        }

    }
}


