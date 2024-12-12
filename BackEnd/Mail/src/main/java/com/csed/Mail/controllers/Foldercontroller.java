package com.csed.Mail.controllers;

import com.csed.Mail.Services.FolderServices;
import com.csed.Mail.model.Dtos.FolderDto;

import com.csed.Mail.model.FolderEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/folder")
public class Foldercontroller {
   private final FolderServices folderServices;

   public Foldercontroller(FolderServices folderServices) {
       this.folderServices = folderServices;
   }

    @GetMapping("/show/{id}")
    public ResponseEntity<List<FolderDto>> showFolders(@PathVariable Long id) {
        List<FolderDto> folders = folderServices.showfolders(id);
        return new ResponseEntity<>(folders, HttpStatus.OK);
    }

 @PostMapping("/create")
public ResponseEntity<?> create(@RequestBody FolderDto folderDto) {
       try {
   return new ResponseEntity<>(folderServices.create(folderDto), HttpStatus.CREATED);
       } catch (IllegalArgumentException e) {
    return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
      }
     }
 }