package com.csed.Mail.Services;

import com.csed.Mail.model.Dtos.MailDto;
import com.csed.Mail.model.MailEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface MailService {

    Page<MailEntity> getEmailsByFolderId(Long folderId, Pageable pageable);
    List<MailDto> getListEmailsByFolderId(Long folderId);

    MailEntity sendMail(MailEntity mailEntity);
}
