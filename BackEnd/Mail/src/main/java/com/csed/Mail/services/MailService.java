package com.csed.Mail.Services;

import com.csed.Mail.model.Dtos.MailDto;
import com.csed.Mail.model.Dtos.MoveDto;
import com.csed.Mail.model.MailEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface MailService {
    List<MailDto> getListEmailsByFolderId(Long folderId);

    List<MailDto> sortDtoMailsByDate(List<MailDto> dtoMails);

    List<MailDto> sortDtoMailsByImportance(List<MailDto> dtoMails);

    List<MailDto> getPage(List<MailDto> mailDtos, int pageNumber, int pageSize);
    void move(MoveDto moveDto);
    void trash(MoveDto moveDto);
}
