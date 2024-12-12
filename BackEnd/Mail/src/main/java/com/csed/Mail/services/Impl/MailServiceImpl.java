package com.csed.Mail.Services.Impl;

import com.csed.Mail.Services.MailService;
import com.csed.Mail.model.MailEntity;
import com.csed.Mail.repositories.MailRepository;

import jakarta.persistence.Entity;
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
        //save it if in draft (exists) or not
        return mailRepository.save(mailEntity);
    }

}
