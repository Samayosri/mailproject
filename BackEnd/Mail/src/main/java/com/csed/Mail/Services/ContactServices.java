package com.csed.Mail.Services;

import com.csed.Mail.model.ContactEntity;
import com.csed.Mail.model.Dtos.ContactDto;
import com.csed.Mail.model.UserEntity;
import com.csed.Mail.repositories.ContactsRepository;
import com.csed.Mail.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ContactServices {
    private final ContactsRepository contactsRepository;
    private  final UserRepository userRepository;

    public ContactServices(ContactsRepository contactsRepository, UserRepository userRepository) {
        this.contactsRepository = contactsRepository;
        this.userRepository = userRepository;
    }

    public List<ContactDto> getAllContacts(Long ownerId){
        List<ContactEntity> Contacts = contactsRepository.findByOwnerId(ownerId);
        List<ContactDto> userContacts  = new ArrayList<>();
        for (ContactEntity c : Contacts) {
            userContacts.add(c.getcontactdto());
        }
        if (userContacts.isEmpty())
            throw new IllegalArgumentException("THIS USER HAS NO CONTACTS");
        return userContacts;
    }
    public  ContactDto create(ContactDto contactDto){
        Optional<ContactEntity> existingContact = contactsRepository.findByName(contactDto.getName());
        if((existingContact.isEmpty())){
            contactDto.setId(null);
            ContactEntity contact=contactDto.getcontact();
            contact.setOwner(userRepository.findById(contactDto.getOwnerid()).get());
            contact.setEmailAddress(contactDto.getEmailAddress());
            contactsRepository.save(contact);
            return contact.getcontactdto();

        }
        throw new IllegalArgumentException("this contact already exists");
    }
    public  ContactDto edit(Long id,ContactDto contactDto){
        if(id==contactDto.getId()){
            Optional<ContactEntity> existingContact = contactsRepository.findById(id);
            if((existingContact.isPresent())){
                ContactEntity contact=contactDto.getcontact();
                contact.setOwner(userRepository.findById(contactDto.getOwnerid()).get());
                contact.setEmailAddress(contactDto.getEmailAddress());
                return contactsRepository.save(contact).getcontactdto();
            }
            else
                throw new IllegalArgumentException("this contact doesn't exist");
        }
        throw new IllegalArgumentException("Wrong Id");
    }
    public void delete(Long id){
        Optional<ContactEntity> existingContact = contactsRepository.findById(id);
        if((existingContact.isPresent())){
           contactsRepository.deleteById(id);
            UserEntity user = existingContact.get().getOwner();
            user.getContacts().remove(existingContact.get());
            userRepository.save(user);

        }
    }
}
