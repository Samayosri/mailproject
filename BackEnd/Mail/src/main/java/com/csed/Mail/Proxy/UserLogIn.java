package com.csed.Mail.Proxy;

import com.csed.Mail.model.Dtos.UserDto;
import com.csed.Mail.model.UserEntity;
import com.csed.Mail.repositories.UserRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
public class UserLogIn implements  IUserLogIn{
     private final UserRepository userRepository;

    public UserLogIn( UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public UserDto makelogin(UserDto userDto){
        Optional<UserEntity> userEntity = userRepository.findByEmailAddress(userDto.getEmailAddress());
        userEntity.get().setPassword(null);
        return userEntity.get().getuserdto();
    }
}
