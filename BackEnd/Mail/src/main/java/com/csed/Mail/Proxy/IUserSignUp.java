package com.csed.Mail.Proxy;

import com.csed.Mail.model.Dtos.UserDto;
import org.apache.catalina.User;

public interface IUserSignUp {
    public UserDto makesignup(UserDto userDto);
}
