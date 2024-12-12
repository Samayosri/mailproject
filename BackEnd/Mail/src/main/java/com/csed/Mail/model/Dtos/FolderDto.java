package com.csed.Mail.model.Dtos;

import com.csed.Mail.model.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FolderDto {
    private UserEntity ownerId;
    private String name;
    private Long id;

}
