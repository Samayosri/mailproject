package com.csed.Mail.Proxy;

import com.csed.Mail.controllers.UserController;
import com.csed.Mail.model.Dtos.UserDto;
import com.csed.Mail.model.UserEntity;
import com.csed.Mail.repositories.UserRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
public class UserProxySignUp implements  IUserSignUp{
     private final UserRepository userRepository;
     private final UserSignUp userSignUp;

    public UserProxySignUp(UserRepository userRepository, UserSignUp userSignUp) {
        this.userRepository = userRepository;
        this.userSignUp = userSignUp;
    }

    public UserDto makesignup(UserDto userDto){
        Optional<UserEntity> userEntity = userRepository.findByEmailAddress(userDto.getEmailAddress());
        if (userEntity.isPresent()) {
            throw new IllegalArgumentException("You already have an account.");
        }
      return userSignUp.makesignup(userDto);
    }
}
