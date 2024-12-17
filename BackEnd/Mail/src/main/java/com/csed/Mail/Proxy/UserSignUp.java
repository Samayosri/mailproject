package com.csed.Mail.Proxy;

import com.csed.Mail.Services.PasswordEncoder;
import com.csed.Mail.model.Dtos.UserDto;
import com.csed.Mail.model.FolderEntity;
import com.csed.Mail.model.UserEntity;
import com.csed.Mail.repositories.FolderRepository;
import com.csed.Mail.repositories.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class UserSignUp implements IUserSignUp{
    private final UserRepository userRepository;
    private final FolderRepository folderRepository;
    private final PasswordEncoder passwordEncoder;

    public UserSignUp(UserRepository userRepository, FolderRepository folderRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.folderRepository = folderRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDto makesignup(UserDto userDto){
        String encodedPassword = passwordEncoder.encodePassword(userDto.getPassword());
        userDto.setPassword(encodedPassword);
        userDto.setId(null);

        UserEntity newCustomer = userDto.getuser();
        newCustomer = userRepository.save(newCustomer);
        createDefaultFolders(newCustomer);
        return newCustomer.getuserdto();
    }

    private void createDefaultFolders(UserEntity user) {
        FolderEntity newfolder = FolderEntity.builder().id(null).name("Inbox").owner(user).userId(user.getId()).build();
        folderRepository.save(newfolder);
        newfolder= FolderEntity.builder().userId(user.getId()).id(null).name("Sent").owner(user).build();
        folderRepository.save(newfolder);
        newfolder= FolderEntity.builder().userId(user.getId()).id(null).name("Drafts").owner(user).build();
        folderRepository.save(newfolder);
        newfolder= FolderEntity.builder().userId(user.getId()).id(null).name("Trash").owner(user).build();
        folderRepository.save(newfolder);
    }
}
