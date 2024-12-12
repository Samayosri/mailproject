package com.csed.Mail.Services;

import com.csed.Mail.model.Dtos.UserDto;
import com.csed.Mail.model.FolderEntity;
import com.csed.Mail.model.UserEntity;
import com.csed.Mail.repositories.FolderRepository;
import com.csed.Mail.repositories.UserRepository;
import com.csed.Mail.mappers.impl.UserMapperImpl;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

@Service
public class UserServices {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
private  final FolderRepository folderRepository;
    public UserServices(UserRepository userRepository, PasswordEncoder passwordEncoder, FolderRepository folderRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.folderRepository=folderRepository;
    }

    public UserDto login(UserDto userDto) throws IllegalArgumentException {
        Optional<UserEntity> userEntity = userRepository.findByEmailAddress(userDto.getEmailAddress());
        if (userEntity.isEmpty()) {
            throw new IllegalArgumentException("Account not found. Sign up to continue.");
        } else if (!passwordEncoder.checkPassword(userDto.getPassword(), userEntity.get().getPassword())) {
            throw new IllegalArgumentException("Wrong password");
        } else {
            userEntity.get().setPassword(null);
            return userEntity.get().getuserdto();
        }
    }

    public UserDto signup(UserDto userDto) throws IllegalArgumentException {
        Optional<UserEntity> userEntity = userRepository.findByEmailAddress(userDto.getEmailAddress());
        if (userEntity.isPresent()) {
            throw new IllegalArgumentException("You already have an account.");
        }

        String encodedPassword = passwordEncoder.encodePassword(userDto.getPassword());
        userDto.setPassword(encodedPassword);
        userDto.setId(null);

        UserEntity newCustomer = userDto.getuser();
        newCustomer = userRepository.save(newCustomer);
        createDefaultFolders(newCustomer);


        return newCustomer.getuserdto();
    }

    private void createDefaultFolders(UserEntity user) {
        FolderEntity newfolder = FolderEntity.builder().id(null).name("Inbox").owner(user).build();
        folderRepository.save(newfolder);
        newfolder= FolderEntity.builder().id(null).name("Sent").owner(user).build();
        folderRepository.save(newfolder);
        newfolder= FolderEntity.builder().id(null).name("Draft").owner(user).build();
        folderRepository.save(newfolder);
        newfolder= FolderEntity.builder().id(null).name("Trash").owner(user).build();
        folderRepository.save(newfolder);
    }}
