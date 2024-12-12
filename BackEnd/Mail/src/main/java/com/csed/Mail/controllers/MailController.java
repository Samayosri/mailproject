package com.csed.Mail.controllers;

import com.csed.Mail.Services.FolderService;
import com.csed.Mail.mappers.Mapper;
import com.csed.Mail.model.Dtos.MailDto;
import com.csed.Mail.model.Dtos.UserDto;
import com.csed.Mail.model.MailEntity;
import com.csed.Mail.Services.MailService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mails")
@CrossOrigin(origins = "http://localhost:5173")
public class MailController {
    private MailService mailService;
    private FolderService folderService;
    private Mapper<MailEntity, MailDto> mailMapper;

    public MailController(MailService mailService, Mapper<MailEntity, MailDto> mailMapper, FolderService folderService) {
        this.mailService = mailService;
        this.mailMapper = mailMapper;
        this.folderService =folderService;
    }

    @GetMapping("/{folder_id}")
    Page<MailDto> getMailsByFolderId(@PathVariable long folderId, @RequestBody UserDto user, Pageable pageable)
    {
        Page<MailEntity> mails = mailService.findAllByFolder(folderId, user.getId(), pageable);
        return mails.map(mailMapper::mapToDto);
    }

    @PostMapping("/send")
    public ResponseEntity<MailDto> sendMail(@RequestBody MailDto mailDto) {
        MailEntity sentMail = mailMapper.mapFromDto(mailDto);

        MailEntity savedMail = mailService.sendMail(sentMail);
        folderService.sendMail(sentMail);

        MailDto responseDto = mailMapper.mapToDto(savedMail);

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

}
