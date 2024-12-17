package com.csed.Mail.model.Dtos;


import com.csed.Mail.model.ContactEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private Long ownerId;
    private String name;
    private List<String> emailAddress=new ArrayList<>();
    @JsonIgnore
    public ContactEntity getContact(){
        return ContactEntity .builder()
                .id(id)
                .name(name)
                .build();
    }

}
