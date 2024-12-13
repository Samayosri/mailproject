package com.csed.Mail.mappers.impl;

import com.csed.Mail.mappers.Mapper;
import com.csed.Mail.model.AttachmentEntity;
import com.csed.Mail.model.Dtos.MailDto;
import com.csed.Mail.model.MailEntity;
import com.csed.Mail.model.UserEntity;
import com.csed.Mail.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;

@Component
public class MailMapperImpl implements Mapper<MailEntity,MailDto> {


    private final UserRepository userRepository;
    @Autowired
    public MailMapperImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public MailDto mapToDto(MailEntity mailEntity) {
        MailDto mailDto = MailDto.builder().
                id(mailEntity.getId()).
                senderId(mailEntity.getSender().getId()).
                creationDate(mailEntity.getCreationDate()).subject(mailEntity.
                getSubject()).
                body(mailEntity.getBody()).
                toReceivers(mailEntity.getToReceivers()).
                ccReceivers(mailEntity.getCcReceivers()).
                bccReceivers(mailEntity.getBccReceivers()).
                importance(mailEntity.getImportance()).
                attachments(new ArrayList<>()).
                build();
        for(AttachmentEntity a : mailEntity.getAttachments()){
            mailDto.getAttachments().add(a.getDto());
        }
        return mailDto;

    }

    public MailEntity mapFromDto(MailDto mailDto)
    {
         MailEntity mailEntity =  MailEntity.builder().
                id(mailDto.getId()).
                creationDate(mailDto.getCreationDate()).
                 subject(mailDto.getSubject()).
                body(mailDto.getBody()).
                toReceivers(mailDto.getToReceivers()).
                ccReceivers(mailDto.getCcReceivers()).
                bccReceivers(mailDto.getBccReceivers()).
                importance(mailDto.getImportance()).
                attachments(new ArrayList<>()).folders(new ArrayList<>()).
                build();

         Optional<UserEntity> user = userRepository.findById(1L);
         if(user.isEmpty()){
             throw new IllegalArgumentException("user with mail not exist !!! ghost sending email");}
            mailEntity.setSender(user.get());
           return mailEntity;


    }
}
