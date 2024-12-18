package com.csed.Mail.controllers;

import com.csed.Mail.ContactSearch.ContactFacade;
import com.csed.Mail.ContactSearch.Dtos.ContactCriteriaDto;
import com.csed.Mail.Search.Dtos.CriteriaDto;
import com.csed.Mail.Services.ContactServices;
import com.csed.Mail.Sorting.Impl.SortContact;
import com.csed.Mail.model.Dtos.ContactDto;
import com.csed.Mail.repositories.ContactsRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/contact")
@CrossOrigin(origins = "http://localhost:5173")
public class ContactsController {
    private final ContactServices contactServices;
    private final ContactsRepository contactsRepository;
    private  final SortContact sortContact;
    private final ContactFacade contactFacade;

    public ContactsController(ContactServices contactServices, ContactsRepository contactsRepository, SortContact sortContact, ContactFacade contactFacade) {
        this.contactServices = contactServices;
        this.contactsRepository = contactsRepository;
        this.sortContact = sortContact;
        this.contactFacade = contactFacade;
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
    @PostMapping("/sort")

    public ResponseEntity<?> sort(@RequestBody List<ContactDto> contactDtos) throws IllegalArgumentException {
        List<ContactDto> sortedContacts = sortContact.SortingContact(contactDtos);
            return new ResponseEntity<>(sortedContacts, HttpStatus.OK);

    }
    @PostMapping("search/{userId}")
    public ResponseEntity<?> searchMails(
            @PathVariable Long userId,
            @RequestBody ContactCriteriaDto contactDto
    ) { // use search service
        try {
            return new ResponseEntity<>(contactFacade.getContacts(userId, contactDto), HttpStatus.OK);
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

        }
    }
    }