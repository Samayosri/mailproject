package com.csed.Mail.services.Impl;

import com.csed.Mail.model.Dtos.MailDto;
import com.csed.Mail.model.MailEntity;
import com.csed.Mail.repositories.MailRepository;
import com.csed.Mail.services.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class MailServiceImpl implements MailService {
    MailRepository mailRepository;

    @Autowired
    public MailServiceImpl(MailRepository mailRepository) {
        this.mailRepository = mailRepository;
    }

    @Override
    public Page<MailEntity> findAllByFolder(Long userId, Long folderId, Pageable pageable) {
        return mailRepository.findEmailsInFolderWithAttachments(userId, folderId, pageable);
    }

    @Override
    public MailEntity sendMail(MailEntity mailEntity) {
            return mailRepository.save(mailEntity);
    }

}
