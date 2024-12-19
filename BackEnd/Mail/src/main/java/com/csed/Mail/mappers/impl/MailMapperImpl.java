package com.csed.Mail.mappers.impl;

import com.csed.Mail.mappers.Mapper;
import com.csed.Mail.model.AttachmentEntity;
import com.csed.Mail.model.Dtos.AttachmentDto;
import com.csed.Mail.model.Dtos.MailDto;
import com.csed.Mail.model.MailEntity;
import com.csed.Mail.model.UserEntity;
import com.csed.Mail.repositories.AttachmentRepository;
import com.csed.Mail.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class MailMapperImpl implements Mapper<MailEntity,MailDto> {
    private final UserRepository userRepository;
    private final AttachmentRepository attachmentRepository;

    public AttachmentEntity getAttch(Long id){
        return attachmentRepository.findById(id).orElseThrow(()->new IllegalArgumentException("att not found"));
    }
    public List<Long> getList(List<AttachmentDto> frontreq){
        List<Long> ids = new ArrayList<>();
        for(AttachmentDto attachmentDto : frontreq){
            if(attachmentDto.getId()==null){
                AttachmentEntity created = attachmentRepository.save(attachmentDto.getAttachment());
                ids.add(created.getId());
            }
            else{
                ids.add(attachmentDto.getId());
            }
        }
        return ids;
    }


    @Autowired
    public MailMapperImpl(UserRepository userRepository, AttachmentRepository attachmentRepository) {
        this.userRepository = userRepository;
        this.attachmentRepository = attachmentRepository;
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
                name(mailEntity.getSender().getName()).
                folder(mailEntity.getState()).
                toReceivers(mailEntity.getToReceivers()).
                ccReceivers(mailEntity.getCcReceivers()).
                bccReceivers(mailEntity.getBccReceivers()).
                importance(mailEntity.getImportance()).
                attachments(new ArrayList<>()).
                build();
        for(Long id : mailEntity.getAttachments()){
            AttachmentEntity attachment = getAttch(id);
            mailDto.getAttachments().add(attachment.getDto());
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
                ccReceivers(mailDto.getCcReceivers()==null ? new ArrayList<>():mailDto.getCcReceivers()).
                bccReceivers(mailDto.getBccReceivers()==null ? new ArrayList<>():mailDto.getBccReceivers()).
                importance(mailDto.getImportance() ).
                attachments(new ArrayList<>()).folder(null).state(mailDto.getFolder()).
                build();

         Optional<UserEntity> user = userRepository.findById(mailDto.getSenderId());
         if(user.isEmpty()) {
             throw new IllegalArgumentException("user with mail not exist !!! ghost sending email");
         }
        mailEntity.setSender(user.get());
         mailEntity.setAttachments(getList(mailDto.getAttachments()));

//       if (!user.get().getEmailAddress().equals(mailDto.getSenderMailAddress())) {
//            System.out.println("hellooooo");
//            throw new IllegalArgumentException("The user id and the email address are not compatible.");
//        } at the sent the sender mail is not setted for now uncomment it after full merge with ui
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
