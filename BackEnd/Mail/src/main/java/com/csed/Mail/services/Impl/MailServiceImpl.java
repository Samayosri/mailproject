package com.csed.Mail.Services.Impl;

import com.csed.Mail.Services.MailService;
import com.csed.Mail.mappers.Mapper;
import com.csed.Mail.model.Dtos.MailDto;
import com.csed.Mail.model.FolderEntity;
import com.csed.Mail.model.MailEntity;
import com.csed.Mail.repositories.FolderRepository;
import com.csed.Mail.repositories.MailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class MailServiceImpl implements MailService {
    MailRepository mailRepository;
    FolderRepository folderRepository;
    private final Mapper<MailEntity, MailDto> mailMapper;

    @Autowired
    public MailServiceImpl(MailRepository mailRepository, FolderRepository folderRepository, Mapper<MailEntity, MailDto> mailMapper) {
        this.mailRepository = mailRepository;
        this.folderRepository = folderRepository;
        this.mailMapper = mailMapper;
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
    public List<MailDto> getListEmailsByFolderId(Long folderId) throws RuntimeException{
        Optional<FolderEntity> folder = folderRepository.findById(folderId);
        if(folder.isPresent()){
            List<MailDto> mailDtos = new ArrayList<>();
            for(MailEntity m : folder.get().getEmails()){
                mailDtos.add(mailMapper.mapToDto(m));
            }
            return mailDtos;
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
