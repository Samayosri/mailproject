package com.csed.Mail.Pagination;

import com.csed.Mail.model.Dtos.MailDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Pager {
    public List<MailDto> page(List<MailDto> mailDtos, int pageNumber, int pageSize) {
        int fromIndex = pageNumber * pageSize;
        if (fromIndex >= mailDtos.size()) {
            return new ArrayList<>();
        }

        int toIndex = Math.min(fromIndex + pageSize, mailDtos.size());

        return mailDtos.subList(fromIndex, toIndex);
    }
}
