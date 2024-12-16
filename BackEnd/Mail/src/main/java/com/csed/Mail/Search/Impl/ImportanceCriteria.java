package com.csed.Mail.Search.Impl;

import com.csed.Mail.Search.Criteria;
import com.csed.Mail.model.Dtos.MailDto;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

public class ImportanceCriteria extends Criteria {
    public ImportanceCriteria(@NonNull String keyword) {
        super(keyword);
    }

    @Override
    public List<MailDto> meetCriteria(List<MailDto> mailDtos) {
        /// convert keyword to Integer
        Integer importanceKeyword;
        try {
            importanceKeyword = Integer.parseInt(getKeyword());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Keyword must be integer ");
        }
        List<MailDto> result = new ArrayList<>();
        for(MailDto mailDto : mailDtos){
            if(mailDto.getImportance()!=null &&mailDto.getImportance().equals(importanceKeyword)){
                result.add(mailDto);
            }
        }
        return result;
    }
}
