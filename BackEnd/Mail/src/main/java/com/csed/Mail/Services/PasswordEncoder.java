package com.csed.Mail.Services;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;


@Service
public class  PasswordEncoder {

    public String encodePassword(String rawPassword) {

        return BCrypt.hashpw(rawPassword, BCrypt.gensalt());
    }

    public boolean checkPassword(String rawPassword, String storedHashedPassword) {
        return BCrypt.checkpw(rawPassword, storedHashedPassword);
    }

}