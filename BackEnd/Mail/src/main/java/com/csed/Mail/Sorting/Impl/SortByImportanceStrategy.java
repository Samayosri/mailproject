package com.csed.Mail.Sorting.Impl;

import com.csed.Mail.Sorting.SortingStrategy;
import com.csed.Mail.model.Dtos.MailDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component("importance")
public class SortByImportanceStrategy implements SortingStrategy {
    @Override
    public List<MailDto> sort(List<MailDto> mailDtos) {
        if (mailDtos == null)
            return new ArrayList<>();
        return mailDtos.
                stream().
                sorted(
                        Comparator.
                                comparing(MailDto::getImportance).
                                thenComparing(MailDto::getCreationDate, Comparator.reverseOrder())
                ).
                collect(Collectors.toList());
    }
}
