package com.csed.Mail.Services;

import com.csed.Mail.model.MailEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface MailService {

    Page<MailEntity> findAllByFolder(Long userId, Long folderId, Pageable pageable);

    MailEntity sendMail(MailEntity mailEntity);
}
