package com.csed.Mail.model.Dtos;

import com.csed.Mail.model.ContactEntity;
import com.csed.Mail.model.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactDto {
    private  Long id;
    private Long ownerid;
    private String name;
    private List<String> emailAddress=new ArrayList<>();
    @JsonIgnore
    public ContactEntity getcontact(){
        return ContactEntity.builder()
                .id(id)
                .name(name)
                .build();
    }
}
