package com.csed.Mail.ContactSearch.Impl;

import com.csed.Mail.ContactSearch.ContactCriteria;
import com.csed.Mail.model.Dtos.ContactDto;

import java.util.ArrayList;
import java.util.List;

public class ContactNameCriteria extends ContactCriteria {
    public ContactNameCriteria(String keyword) {
        super(keyword);
    }

    @Override
    public List<ContactDto> meetCriteria(List<ContactDto> contactDtos) {
        List<ContactDto> result = new ArrayList<>();

        for(ContactDto contactDto : contactDtos) {
            if(contactDto != null &&
                    contactDto.getName() != null &&
                    contactDto.getName().toLowerCase().contains(this.getKeyword().toLowerCase())) {
                result.add(contactDto);
            }
        }
        return result;
    }
}
