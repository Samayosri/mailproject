package com.csed.Mail.Services.Impl;

import com.csed.Mail.Services.MailService;
import com.csed.Mail.model.MailEntity;
import com.csed.Mail.repositories.FolderRepository;
import com.csed.Mail.repositories.MailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class MailServiceImpl implements MailService {
    MailRepository mailRepository;
    FolderRepository folderRepository;

    @Autowired
    public MailServiceImpl(MailRepository mailRepository, FolderRepository folderRepository) {
        this.mailRepository = mailRepository;
        this.folderRepository = folderRepository;
    }

    @Override
    public Page<MailEntity> getEmailsByFolderId(Long folderId, Pageable pageable) throws RuntimeException{
        if(folderRepository.existsById(folderId)) {
            return mailRepository.findByFoldersId(folderId, pageable);
        }
        else {
            throw new RuntimeException("Folder does not exit");
        }
    }
    @Override
    public MailEntity sendMail(MailEntity mailEntity) {
        //save it if in draft (exists) or not
        return mailRepository.save(mailEntity);
    }

}
