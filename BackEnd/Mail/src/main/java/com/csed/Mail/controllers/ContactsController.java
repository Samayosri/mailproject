package com.csed.Mail.controllers;

import com.csed.Mail.Services.ContactServices;
import com.csed.Mail.model.Dtos.ContactDto;
import com.csed.Mail.repositories.ContactsRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/contact")
@CrossOrigin(origins = "http://localhost:5173")
public class ContactsController {
    private final ContactServices contactServices;
    private final ContactsRepository contactsRepository;

    public ContactsController(ContactServices contactServices, ContactsRepository contactsRepository) {
        this.contactServices = contactServices;
        this.contactsRepository = contactsRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAllContacts(@PathVariable Long id) throws IllegalArgumentException {
        try {
            return new ResponseEntity<>(contactServices.getAllContacts(id), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("")
    public ResponseEntity<?> Create(@RequestBody ContactDto contactDto) throws IllegalArgumentException {
        try {
            return new ResponseEntity<>(contactServices.create(contactDto), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable Long id, @RequestBody ContactDto contactDto) throws IllegalArgumentException {
        try {
            return new ResponseEntity<>(contactServices.edit(id,contactDto), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        contactServices.delete(id);
        return new ResponseEntity<>( HttpStatus.OK);

    }
    }