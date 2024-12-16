package com.csed.Mail.Search.Impl;

import com.csed.Mail.Search.Criteria;
import com.csed.Mail.model.Dtos.MailDto;
import lombok.NonNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DateCriteria extends Criteria {

    public DateCriteria(@NonNull String keyword) {
        super(keyword);
    }

    @Override
    public List<MailDto> meetCriteria(List<MailDto> mailDtos) {
        List<MailDto> result = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        for (MailDto mailDto : mailDtos) {
            if (mailDto.getCreationDate() != null) {
                // Convert creationDate to string in "yyyy-MM-dd" format
                String creationDate = dateFormat.format(mailDto.getCreationDate());

                if (creationDate.contains(getKeyword())) {
                    result.add(mailDto);
                }
            }
        }

        return result;
    }
}
