package com.csed.Mail.Services;


import com.csed.Mail.model.Dtos.UserDto;
import com.csed.Mail.model.UserEntity;
import com.csed.Mail.repositories.UserRepository;
import org.springframework.stereotype.Service;
import  com.csed.Mail.mappers.impl.UserMapperImpl;
import java.util.Optional;
@Service
public class UserServices {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapperImpl userMapper;
    public UserServices(UserRepository userRepository, PasswordEncoder passwordEncoder,UserMapperImpl userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper= userMapper;
    }

    public UserDto login(UserDto userDto) throws IllegalArgumentException{
        Optional<UserEntity> userEntity = userRepository.findByEmailAddress(userDto.getEmailAddress());
        if(userEntity.isEmpty()){
           throw new IllegalArgumentException("Account not found signup to continue");
        }
        else if(!passwordEncoder.checkPassword(userDto.getPassword(),userEntity.get().getPassword())){
            throw new IllegalArgumentException("Wrong password");
        }
        else{
            userEntity.get().setPassword(null);
            return userMapper.mapToDto(userEntity.get());
        }
    }
    public UserDto signup(UserDto userDto) throws IllegalArgumentException {
        Optional<UserEntity> userEntity = userRepository.findByEmailAddress(userDto.getEmailAddress());
        if (userEntity.isPresent()) {
            throw new IllegalArgumentException("You already have an account");
        }
        String encodedPassword = passwordEncoder.encodePassword(userDto.getPassword());
        userDto.setPassword(encodedPassword);
        userDto.setId(null);

        UserEntity newcustomer= userRepository.save(userMapper.mapFromDto(userDto));
        return userMapper.mapToDto(newcustomer);

    }



}



