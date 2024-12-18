package com.csed.Mail.ContactSearch;

import com.csed.Mail.model.Dtos.ContactDto;
import com.csed.Mail.model.Dtos.MailDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class ContactCriteria {
    protected String keyword;
    public abstract List<ContactDto> meetCriteria(List<ContactDto> contactDtos);
}
