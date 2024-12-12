package com.csed.Mail.model.Dtos;

import com.csed.Mail.enums.Importance;
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
public class MailDto {
    private Long id;
    private List<String> toReceivers;
    private List<String> ccReceivers;
    private Importance importance;
    private List<AttachmentDto> attachments = new ArrayList<>();
}
