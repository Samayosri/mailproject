package com.csed.Mail.controllers;

import com.csed.Mail.Search.Criteria;
import com.csed.Mail.Search.Impl.DateCriteria;
import com.csed.Mail.Search.Impl.ImportanceCriteria;
import com.csed.Mail.Services.FolderService;
import com.csed.Mail.mappers.Mapper;
import com.csed.Mail.model.Dtos.MailDto;
import com.csed.Mail.model.Dtos.MoveDto;
import com.csed.Mail.model.MailEntity;
import com.csed.Mail.Services.MailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mail")
@CrossOrigin(origins = "http://localhost:5173")
public class MailController {
    private final MailService mailService;
    private final FolderService folderService;
    private final Mapper<MailEntity, MailDto> mailMapper;

    public MailController(MailService mailService, Mapper<MailEntity, MailDto> mailMapper, FolderService folderService) {
        this.mailService = mailService;
        this.mailMapper = mailMapper;
        this.folderService = folderService;
    }
    @PostMapping("/send")
    public ResponseEntity<?> sendMail(@RequestBody MailDto mailDto) {
        try {
            MailEntity sentMail = mailMapper.mapFromDto(mailDto);
            return new ResponseEntity<>(mailMapper.mapToDto(folderService.sendMail(sentMail)), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/draft")
    public ResponseEntity<?> draftMail(@RequestBody MailDto mailDto) {
        try {
            return new ResponseEntity<>(
                    mailMapper.mapToDto(folderService.draftMail(mailMapper.mapFromDto(mailDto))),
                    HttpStatus.CREATED
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{folderId}")
    public ResponseEntity<?> getMails(
            @PathVariable Long folderId,
            @RequestParam(required = false,defaultValue = "date") String sort,
            @RequestParam Integer pageNumber,
            @RequestParam(required = false,defaultValue = "5") Integer pageSize

    ) {
        try {
            List<MailDto> mails = mailService.getListEmailsByFolderId(folderId);
            if(sort.equals("date")){
                mails = mailService.sortDtoMailsByDate(mails);
                mails = mailService.getPage(mails,pageNumber,pageSize);
                return new ResponseEntity<>(mails,HttpStatus.OK);
            }
            else if(sort.equals("priority")){
                mails = mailService.sortDtoMailsByImportance(mails);
                mails = mailService.getPage(mails,pageNumber,pageSize);
                return new ResponseEntity<>(mails,HttpStatus.OK);
            }
            else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Sorting");

            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("get/{userId}")
    public ResponseEntity<?> searchMails(
            @PathVariable Long userId,
            @RequestParam(required = false,defaultValue = "null") Long folderId,
            @RequestParam String searchMethod,
            @RequestParam String searchWord,
            @RequestParam Integer pageNumber,
            @RequestParam(required = false,defaultValue = "5") Integer pageSize

    ) { // use search service
        Criteria criteria = new ImportanceCriteria("2");
        return new ResponseEntity<>(criteria.meetCriteria(mailService.getListEmailsByFolderId(folderId)), HttpStatus.OK);
    }
    @PutMapping("/move")
    public ResponseEntity<?> move(@RequestBody MoveDto moveDto){
        return null; // will be done by cammands

    }
    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestBody MoveDto moveDto){
        return null; // will be done by commands

    }



}
