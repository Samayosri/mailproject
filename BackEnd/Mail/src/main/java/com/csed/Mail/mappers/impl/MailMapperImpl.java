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
import java.util.List;
import java.util.Optional;

@Component
public class MailMapperImpl implements Mapper<MailEntity,MailDto> {
    private final UserRepository userRepository;
    @Autowired
    public MailMapperImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public MailDto mapToDto(MailEntity mailEntity) {
        MailDto mailDto = MailDto.builder().
                id(mailEntity.getId()).
                senderId(mailEntity.getSender().getId()).
                senderMailAddress(mailEntity.getSender().getEmailAddress()).
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
    @Override
    public List<MailDto> mapListToDto(List<MailEntity> mailEntities) {
        List<MailDto> mailDtos = new ArrayList<>();
        for(MailEntity m : mailEntities){
            mailDtos.add(mapToDto(m));
        }
        return mailDtos;
    }

    @Override
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

         Optional<UserEntity> user = userRepository.findById(mailDto.getSenderId());
         if(user.isEmpty()) {
             throw new IllegalArgumentException("user with mail not exist !!! ghost sending email");
         }

//        if (!user.get().getEmailAddress().equals(mailDto.getSenderMailAddress())) {
//            System.out.println("hellooooo");
//            throw new IllegalArgumentException("The user id and the email address are not compatible.");
//        } at the sent the sender mail is not setted for now uncomment it after full merge with ui

        mailEntity.setSender(user.get());

         return mailEntity;


    }
    @Override
    public List<MailEntity> mapListFromDto(List<MailDto> mailDtos) {
        List<MailEntity> mailEntities = new ArrayList<>();
        for(MailDto m : mailDtos){
            mailEntities.add(mapFromDto(m));
        }
        return mailEntities;
    }
}
