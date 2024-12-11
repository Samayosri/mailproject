package com.csed.Mail.model.Dtos;

import com.csed.Mail.model.MailEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttachmentDto {

    private Long id;
    private MailEntity mail;
    private String fileName;
    private String fileType;
    private byte[] file;
}
