//package com.csed.Mail.controllers;
//
//import com.csed.Mail.mappers.Mapper;
//import com.csed.Mail.model.Dtos.MailDto;
//import com.csed.Mail.model.Dtos.UserDto;
//import com.csed.Mail.model.MailEntity;
//import com.csed.Mail.services.MailService;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//public class MailController {
//    private MailService mailService;
//    private Mapper<MailEntity, MailDto> mailMapper;
//
//    public MailController(MailService mailService, Mapper<MailEntity, MailDto> mailMapper) {
//        this.mailService = mailService;
//        this.mailMapper = mailMapper;
//    }
//
////    @GetMapping("/mails/{folder_id}")
////    Page<MailDto> getMailsByFolderId(@PathVariable("folder_id") long folderId, @RequestBody UserDto user, Pageable pageable)
////    {
////        Page<MailEntity> mails = mailService.findAllByFolder(folderId, user.getId(), pageable);
////        return mails.map(mailMapper::mapToDto);
////    }
//
////    @PostMapping("/mails/send")
////    ResponseEntity<MailDto> sendMail(@RequestBody MailDto)
////    {
////
////    }
//
//}
