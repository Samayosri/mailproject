package com.csed.Mail.controllers;
import com.csed.Mail.model.Dtos.UserDto;
import com.csed.Mail.Services.UserServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    private final UserServices userservices;

    public UserController(UserServices userservices) {
        this.userservices = userservices;
    }
    @PostMapping("/signup")
    public ResponseEntity<?> create(@RequestBody UserDto userDto){
        try {
            return new ResponseEntity<>( userservices.signup(userDto), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDto userDto){
        try {
            return new ResponseEntity<>(userservices.login(userDto), HttpStatus.CREATED);
        }
        catch (IllegalArgumentException e){
            return new ResponseEntity<>( e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
