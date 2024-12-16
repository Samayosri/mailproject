package com.csed.Mail.Search;

import com.csed.Mail.model.Dtos.MailDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Criteria {
    protected String keyword;
    public abstract List<MailDto> meetCriteria(List<MailDto> mailDtos);
}

