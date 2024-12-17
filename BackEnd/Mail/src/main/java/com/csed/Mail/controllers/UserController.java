package com.csed.Mail.controllers;
import com.csed.Mail.Proxy.UserProxy;
import com.csed.Mail.Proxy.UserProxySignUp;
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
    private final UserProxy userProxy;
    private final UserProxySignUp userProxySignUp;

    public UserController(UserServices userservices, UserProxy userProxy, UserProxySignUp userProxySignUp) {
        this.userservices = userservices;
        this.userProxy = userProxy;
        this.userProxySignUp = userProxySignUp;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> create(@RequestBody UserDto userDto){
        try {
            return new ResponseEntity<>(/* userservices.signup(userDto)*/userProxySignUp.makesignup(userDto), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDto userDto){
        try {
            return new ResponseEntity<>(/*userservices.login(userDto)*/ userProxy.makelogin(userDto), HttpStatus.CREATED);
        }
        catch (IllegalArgumentException e){
            return new ResponseEntity<>( e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
