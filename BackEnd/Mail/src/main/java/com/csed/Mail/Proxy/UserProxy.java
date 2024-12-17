package com.csed.Mail.Proxy;

import com.csed.Mail.Services.PasswordEncoder;
import com.csed.Mail.model.Dtos.UserDto;
import com.csed.Mail.model.UserEntity;
import com.csed.Mail.repositories.UserRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
public class UserProxy implements IUserLogIn
{ private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserLogIn userLogIn;
    public UserProxy(PasswordEncoder passwordEncoder, UserRepository userRepository,UserLogIn userLogIn) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.userLogIn=userLogIn;
    }

    public UserDto makelogin(UserDto userDto){
        Optional<UserEntity> userEntity = userRepository.findByEmailAddress(userDto.getEmailAddress());
        if (userEntity.isEmpty()) {
    throw new IllegalArgumentException("Account not found. Sign up to continue.");
} else if (!passwordEncoder.checkPassword(userDto.getPassword(), userEntity.get().getPassword())) {
    throw new IllegalArgumentException("Wrong password");
}
    return userLogIn.makelogin(userDto); }
}

