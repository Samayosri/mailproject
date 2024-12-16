package com.csed.Mail.Sorting;

import com.csed.Mail.model.Dtos.MailDto;

import java.util.List;

public interface SortingStrategy {

    List<MailDto> sort(List<MailDto> mailDtos);
}
