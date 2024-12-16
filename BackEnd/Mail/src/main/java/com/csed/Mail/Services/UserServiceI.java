package com.csed.Mail.Services;

import com.csed.Mail.model.Dtos.UserDto;

public interface UserServiceI {

    public UserDto login(UserDto userDto) throws IllegalArgumentException ;
    public UserDto signup(UserDto userDto) throws IllegalArgumentException ;
}
