package com.csed.Mail.model;

import com.csed.Mail.model.Dtos.ContactDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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
@Entity
public class ContactEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    @ManyToOne
    private  UserEntity owner;
    private String name;
    private List<String> emailAddress=new ArrayList<>();
    @JsonIgnore
    public ContactDto getcontactdto(){
        return ContactDto.builder()
                .name(name)
                .ownerId(owner.getId())
                .emailAddress(emailAddress)
                .id(id)
        .build();
    }
}
