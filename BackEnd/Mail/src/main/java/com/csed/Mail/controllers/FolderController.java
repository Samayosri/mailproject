package com.csed.Mail.controllers;

import com.csed.Mail.Services.FolderServices;
import com.csed.Mail.model.Dtos.FolderDto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/folder")
@CrossOrigin(origins = {"http://localhost:5173","http://localhost:3000","http://localhost:3002"} ,allowCredentials = "true")
public class FolderController {
   private final FolderServices folderServices;

   public FolderController(FolderServices folderServices) {
       this.folderServices = folderServices;
   }

    @GetMapping("/{id}")
    public ResponseEntity<?> getFolders(@PathVariable Long id) {
        try {
            List<FolderDto> folders = folderServices.getFolders(id);
            return new ResponseEntity<>(folders, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

 @PostMapping("/create")
public ResponseEntity<?> create(@RequestBody FolderDto folderDto) {
       try {
   return new ResponseEntity<>(folderServices.create(folderDto), HttpStatus.CREATED);
       } catch (IllegalArgumentException e) {
    return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
      }
     }
    @PutMapping ("/edit/{id}")
    public ResponseEntity<?> edit(@RequestBody FolderDto folderDto,@PathVariable Long id) {
        try {
            folderDto.setId(id);
            return new ResponseEntity<>(folderServices.renaming(folderDto), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            folderServices.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}