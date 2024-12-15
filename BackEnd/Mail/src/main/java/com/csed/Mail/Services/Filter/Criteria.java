package com.csed.Mail.Services.Filter;

import com.csed.Mail.model.Dtos.MailDto;

import java.util.List;

public interface Criteria {
    public List<MailDto> meetCriteria(List<MailDto> persons);
}

