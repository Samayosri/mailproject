package com.csed.Mail.Search.Impl;

import com.csed.Mail.Search.Criteria;
import com.csed.Mail.model.Dtos.AttachmentDto;
import com.csed.Mail.model.Dtos.MailDto;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

public class AttachmentsCriteria extends Criteria {


    public AttachmentsCriteria(@NonNull String keyword) {
        super(keyword.trim());
    }

    @Override
    public List<MailDto> meetCriteria(List<MailDto> mailDtos) {
        List<MailDto> result = new ArrayList<>();
        for(MailDto mailDto : mailDtos) {
            if(mailDto.getAttachments() == null) continue;
            for (AttachmentDto attachmentDto : mailDto.getAttachments()) {
                if(attachmentDto.getName() != null &&
                        attachmentDto.getName().toLowerCase().contains(super.keyword.toLowerCase())) {
                    result.add(mailDto);
                    break;
                }
            }
        }
        return result;
    }
}
