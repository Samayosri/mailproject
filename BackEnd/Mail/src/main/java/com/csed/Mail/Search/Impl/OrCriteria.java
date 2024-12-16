package com.csed.Mail.Search.Impl;

import com.csed.Mail.Search.Criteria;
import com.csed.Mail.model.Dtos.MailDto;
import lombok.NonNull;

import java.util.List;

import java.util.ArrayList;

public class OrCriteria extends Criteria {
    private List<Criteria> criterias;

    public OrCriteria(@NonNull String keyword, List<Criteria> criterias) {
        super(keyword.trim());
        this.criterias = criterias;
    }

    @Override
    public List<MailDto> meetCriteria(List<MailDto> mailDtos) {
        List<MailDto> result = new ArrayList<>();

        for (MailDto mailDto : mailDtos) {
            for (Criteria criteria : criterias) {
                List<MailDto> filteredMails = criteria.meetCriteria(List.of(mailDto));
                if (!filteredMails.isEmpty()) {
                    result.add(mailDto);
                    break;
                }
            }
        }
        return result;
    }
}

