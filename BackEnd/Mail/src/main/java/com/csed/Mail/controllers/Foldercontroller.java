package com.csed.Mail.controllers;

import com.csed.Mail.Services.FolderServices;
import com.csed.Mail.model.Dtos.FolderDto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/folder")
public class Foldercontroller {
    private final FolderServices folderServices;

    public Foldercontroller(FolderServices folderServices) {
        this.folderServices = folderServices;
    }

//    @PostMapping("/create")
//    public ResponseEntity<?> create(@RequestBody FolderDto folderDto) {
//        try {
//            return new ResponseEntity<>(folderServices.create(folderDto), HttpStatus.CREATED);
//        } catch (IllegalArgumentException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }
}