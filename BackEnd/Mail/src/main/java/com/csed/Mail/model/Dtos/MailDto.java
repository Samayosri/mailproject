package com.csed.Mail.model.Dtos;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MailDto {
    private Long id;

    private Long senderId;
    private String senderMailAddress;

    @JsonFormat(pattern = "yyyy-MM-dd 'T' HH:mm:ss")
    private LocalDateTime creationDate;

    private String subject;
    private String body;

    private Set<String> toReceivers = new HashSet<>();
    private Set<String> ccReceivers = new HashSet<>();
    private Set<String> bccReceivers = new HashSet<>();

    private Integer importance;

    private List<AttachmentDto> attachments = new ArrayList<>();
}
