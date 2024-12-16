package com.csed.Mail.Search;

import com.csed.Mail.model.Dtos.MailDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
@AllArgsConstructor
public abstract class Criteria {
    @NonNull
    protected String keyword;
    public abstract List<MailDto> meetCriteria(List<MailDto> mailDtos);
}

