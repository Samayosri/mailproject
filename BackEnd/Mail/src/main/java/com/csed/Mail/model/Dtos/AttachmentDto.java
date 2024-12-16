package com.csed.Mail.model.Dtos;

import com.csed.Mail.model.AttachmentEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttachmentDto {

    private String Name;
    private String Type;
    private String file;

    public AttachmentEntity getAttachment(){
        return AttachmentEntity.builder().
                id(null).
                fileType(Type).
                fileData(file != null ?file.getBytes(StandardCharsets.UTF_8): null).
                build();
    }
}
