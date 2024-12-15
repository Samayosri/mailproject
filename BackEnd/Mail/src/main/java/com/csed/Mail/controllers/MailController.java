package com.csed.Mail.controllers;

import com.csed.Mail.Services.FolderService;
import com.csed.Mail.mappers.Mapper;
import com.csed.Mail.model.Dtos.MailDto;
import com.csed.Mail.model.MailEntity;
import com.csed.Mail.Services.MailService;
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

    @GetMapping("get/{folderId}/{pageNumber}/{pageSize}")
    public ResponseEntity<?> getMails(
            @PathVariable long folderId,
            @PathVariable int pageNumber,
            @PathVariable int pageSize

    ) {
        try {
            return new ResponseEntity<>(mailService.getPage(mailService.sortDtoMailsByImportance(mailService.getListEmailsByFolderId(folderId)), pageNumber, pageSize), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/send")
    public ResponseEntity<?> sendMail(@RequestBody MailDto mailDto) {
        try{
            MailEntity sentMail = mailMapper.mapFromDto(mailDto);
            if(sentMail.getSender() == null){
                throw new IllegalArgumentException("helloo");
            }
            return new ResponseEntity<>(mailMapper.mapToDto(folderService.sendMail(sentMail)), HttpStatus.OK);
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/draft")
    public ResponseEntity<?> draftMail(@RequestBody MailDto mailDto){
        try {
            return new ResponseEntity<>(
                    mailMapper.mapToDto(folderService.draftMail(mailMapper.mapFromDto(mailDto))),
                    HttpStatus.OK
            );
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
