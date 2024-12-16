package com.csed.Mail.Search.Impl;

import com.csed.Mail.Search.Criteria;
import com.csed.Mail.model.Dtos.MailDto;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

public class SubjectCriteria extends Criteria {

    public SubjectCriteria(@NonNull String keyword) {
        super(keyword.trim());
    }

    @Override
    public List<MailDto> meetCriteria(List<MailDto> mailDtos) {
        List<MailDto> result = new ArrayList<>();

        for(MailDto mailDto : mailDtos){
            if(mailDto.getSubject() != null && mailDto.getSubject().toLowerCase().contains(super.keyword.toLowerCase())) {
                result.add(mailDto);
            }
        }
        return result;
    }
}
