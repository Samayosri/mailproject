package com.csed.Mail.model.Dtos;


import com.csed.Mail.model.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private Long id;
    private String name;
    private String emailAddress;
    private String password;
    @JsonIgnore
    public UserEntity getuser(){
        return UserEntity.builder().id(id).emailAddress(emailAddress).password(password).name(name).build();
    }
}