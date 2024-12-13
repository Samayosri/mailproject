package com.csed.Mail.model.Dtos;

import com.csed.Mail.model.AttachmentEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Base64;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttachmentDto {

    private String fileName;
    private String fileType;
    private String file;

    public AttachmentEntity getAttachment(){
        return AttachmentEntity.builder().
                id(null).
                fileType(fileType).
                fileData(file != null ? Base64.getDecoder().decode(file) : null).
                build();
    }
}
