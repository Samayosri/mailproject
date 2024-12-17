package com.csed.Mail.model.Dtos;

import com.csed.Mail.model.AttachmentEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    private String name;
    private String type;
    private String file;

    @JsonIgnore
    public AttachmentEntity getAttachment(){
        return AttachmentEntity.builder().
                fileName(name).
                fileType(type).
                fileData(file != null ?file.getBytes(StandardCharsets.UTF_8): null).
                build();
    }
}
