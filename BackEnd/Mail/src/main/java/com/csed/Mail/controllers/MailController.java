package com.csed.Mail.controllers;

import com.csed.Mail.Search.Criteria;
import com.csed.Mail.Search.CriteriaFacade;
import com.csed.Mail.Search.Dtos.CriteriaDto;
import com.csed.Mail.Search.Impl.DateCriteria;
import com.csed.Mail.Search.Impl.ImportanceCriteria;
import com.csed.Mail.Services.FolderService;
import com.csed.Mail.mappers.Mapper;
import com.csed.Mail.model.Dtos.DeleteDto;
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
@CrossOrigin("*")
public class MailController {
    private final MailService mailService;
    private final FolderService folderService;
    private final Mapper<MailEntity, MailDto> mailMapper;
    private final CriteriaFacade criteriaFacade;

    public MailController(MailService mailService, Mapper<MailEntity, MailDto> mailMapper, FolderService folderService, CriteriaFacade criteriaFacade) {
        this.mailService = mailService;
        this.mailMapper = mailMapper;
        this.folderService = folderService;
        this.criteriaFacade = criteriaFacade;
    }
    @PostMapping("/send")
    public ResponseEntity<?> sendMail(@RequestBody MailDto mailDto) {
        try {
            MailEntity sentMail = mailMapper.mapFromDto(mailDto);
            folderService.sendMail(sentMail);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/draft")
    public ResponseEntity<?> draftMail(@RequestBody MailDto mailDto) {
        try {
            folderService.draftMail(mailMapper.mapFromDto(mailDto));
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("get/{folderId}")
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

    @PostMapping("search/{userId}")
    public ResponseEntity<?> searchMails(
            @PathVariable Long userId,
            @RequestParam(required = false) Long folderId,
            @RequestBody CriteriaDto criteriaDto
            ) { // use search service
        try {
            return new ResponseEntity<>(criteriaFacade.getMails(userId, folderId, criteriaDto), HttpStatus.OK);
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

        }

    }
    @PutMapping("/move")
    public ResponseEntity<?> move(@RequestBody MoveDto moveDto){
        try {
            mailService.move(moveDto);
            return ResponseEntity.status(HttpStatus.OK).body("");

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

        }
    }
    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestBody DeleteDto deleteDto){
        try {
            mailService.trash(deleteDto);
            return ResponseEntity.status(HttpStatus.OK).body("");

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

        }

    }



}
