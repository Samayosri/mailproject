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
    private final MailService mailService;
    private final FolderService folderService;
    private final Mapper<MailEntity, MailDto> mailMapper;

    public MailController(MailService mailService, Mapper<MailEntity, MailDto> mailMapper, FolderService folderService) {
        this.mailService = mailService;
        this.mailMapper = mailMapper;
        this.folderService =folderService;
    }
    @GetMapping("/{folderId}")
    public ResponseEntity<?> getMailsByFolderId(@PathVariable long folderId, Pageable pageable) {
        try {
            Page<MailEntity> mails = mailService.getEmailsByFolderId(folderId, pageable);
            Page<MailDto> mailDtos = mails.map(mailMapper::mapToDto);
            return ResponseEntity.ok(mailDtos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    // id   name    userId
    // 1    inbox   nourId
    // 2    sent    nourId
    // 3    inbox   ahmedId
    // 4    sent    ahmedId
    // 5    work    ahmedId

    @PostMapping("/send")
    public ResponseEntity<?> sendMail(@RequestBody MailDto mailDto) {
        try{
           // System.out.println(mailDto.toString());
            MailEntity sentMail = mailMapper.mapFromDto(mailDto);
            if(sentMail.getSender() == null){
                throw new IllegalArgumentException("helloo");
            }
            //System.out.println(sentMail.toString());

            folderService.sendMail(sentMail);
          return ResponseEntity.status(HttpStatus.OK).body("Email is sent successfully.");
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/draft")
    public ResponseEntity<?> draftMail(@RequestBody MailDto mailDto){
        try {
            MailEntity sentMail = mailMapper.mapFromDto(mailDto);
            folderService.draftMail(sentMail);
            return ResponseEntity.status(HttpStatus.CREATED).body("Email is sent successfully.");
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
