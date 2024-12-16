package com.csed.Mail.Search.Impl;

import com.csed.Mail.Search.Criteria;
import com.csed.Mail.model.Dtos.MailDto;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

public class ReceiversEmailAddressCriteria extends Criteria {

    public ReceiversEmailAddressCriteria(@NonNull String keyword) {
        super(keyword);
    }

    @Override
    public List<MailDto> meetCriteria(List<MailDto> mailDtos) {
        List<MailDto> result = new ArrayList<>();

        for (MailDto mailDto : mailDtos) {
            if (containsKeyword(mailDto.getToReceivers())
                    || containsKeyword(mailDto.getCcReceivers())
                    || containsKeyword(mailDto.getBccReceivers())) {
                result.add(mailDto);
            }
        }
        return result;
    }

    private boolean containsKeyword(List<String> receivers) {
        if (receivers == null) {
            return false;
        }
        for (String emailAddress : receivers) {
            if (emailAddress.toLowerCase().contains(super.keyword.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
}
