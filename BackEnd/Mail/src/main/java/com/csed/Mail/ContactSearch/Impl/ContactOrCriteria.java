package com.csed.Mail.ContactSearch.Impl;

import com.csed.Mail.ContactSearch.ContactCriteria;
import com.csed.Mail.Search.Criteria;
import com.csed.Mail.model.Dtos.ContactDto;
import com.csed.Mail.model.Dtos.MailDto;

import java.util.ArrayList;
import java.util.List;

public class ContactOrCriteria extends ContactCriteria {
    List<ContactCriteria> criterias;
    public ContactOrCriteria(String keyword, List<ContactCriteria> criterias) {
        super(keyword);
        this.criterias = criterias;
    }


    @Override
    public List<ContactDto> meetCriteria(List<ContactDto> contactDtos) {
        List<ContactDto> result = new ArrayList<>();

        for(ContactDto contactDto : contactDtos) {
            for(ContactCriteria criteria : criterias) {
                List<ContactDto> filteredContacts = criteria.meetCriteria(List.of(contactDto));
                if (!filteredContacts.isEmpty()) {
                    result.add(contactDto);
                    break;
                }
            }
        }
        return result;
    }
}
