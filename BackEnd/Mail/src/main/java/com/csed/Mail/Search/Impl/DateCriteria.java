package com.csed.Mail.Search.Impl;

import com.csed.Mail.Search.Criteria;
import com.csed.Mail.model.Dtos.MailDto;
import lombok.NonNull;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DateCriteria extends Criteria {

    public DateCriteria(@NonNull String keyword) {
        super(keyword);
    }

    @Override
    public List<MailDto> meetCriteria(List<MailDto> mailDtos) {
        List<MailDto> result = new ArrayList<>();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (MailDto mailDto : mailDtos) {
            if (mailDto.getCreationDate() != null) {
                String creationDate = mailDto.getCreationDate().toLocalDate().format(dateFormatter);

                if (creationDate.equals(getKeyword())) {
                    result.add(mailDto);
                }
            }
        }

        return result;
    }
}
