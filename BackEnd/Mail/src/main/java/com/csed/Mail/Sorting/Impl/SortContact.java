package com.csed.Mail.Sorting.Impl;

import com.csed.Mail.Sorting.ISortContact;
import com.csed.Mail.model.Dtos.ContactDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SortContact implements ISortContact {

    @Override
    public List<ContactDto> SortingContact(List<ContactDto> contactDtos) {
        if (contactDtos.isEmpty()) {
            return new ArrayList<>();
        }

        return contactDtos.stream()
                .sorted(
                        Comparator.comparing(ContactDto::getName,String.CASE_INSENSITIVE_ORDER)
                                .thenComparing(ContactDto::getName, Comparator.reverseOrder())
                )
                .collect(Collectors.toList());
    }
}
