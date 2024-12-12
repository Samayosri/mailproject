package com.csed.Mail.model.Dtos;

import com.csed.Mail.model.FolderEntity;
import com.csed.Mail.repositories.UserRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FolderDto {

    private Long id; //used to fetch emails
    private Long userId;
    private String name;
    @JsonIgnore
    public FolderEntity getfolder(){
        return FolderEntity.builder().id(id).userId(userId).name(name).build();
    }
}
