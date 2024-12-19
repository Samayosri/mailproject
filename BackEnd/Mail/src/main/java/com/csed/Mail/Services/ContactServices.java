package com.csed.Mail.Services;

import com.csed.Mail.Sorting.ISortContact;
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

    private final ISortContact iSortContact;

    public ContactServices(ContactsRepository contactsRepository, UserRepository userRepository, ISortContact iSortContact) {
        this.contactsRepository = contactsRepository;
        this.userRepository = userRepository;
        this.iSortContact = iSortContact;
    }

    public List<ContactDto> getAllContacts(Long ownerId){
        List<ContactEntity> Contacts = contactsRepository.findByOwnerId(ownerId);
        List<ContactDto> userContacts  = new ArrayList<>();
        for (ContactEntity c : Contacts) {
            userContacts.add(c.getcontactdto());
        }
        if (userContacts.isEmpty())
            throw new IllegalArgumentException("THIS USER HAS NO CONTACTS");
        return iSortContact.SortingContact(userContacts);
    }
    public  ContactDto create(ContactDto contactDto){
        Optional<ContactEntity> existingContact = contactsRepository.findByNameAndOwnerId(contactDto.getName(),contactDto.getOwnerId());
        if(existingContact.isPresent() && existingContact.get().getOwner().getId().equals(contactDto.getOwnerId()) ){
            throw new IllegalArgumentException("this contact already exists");
        }
        List<String> wrongemails=Checkvalidation(contactDto);
        if(!wrongemails.isEmpty()){
            throw new IllegalArgumentException(wrongemails+" don't exist");
        }
        contactDto.setId(null);
        ContactEntity contact=contactDto.getContact();
        contact.setOwner(userRepository.findById(contactDto.getOwnerId()).get());
        contact.setEmailAddress(contactDto.getEmailAddress());
        contactsRepository.save(contact);
        return contact.getcontactdto();
    }

    public ContactDto edit(Long id, ContactDto contactDto) {
        if (id == contactDto.getId()) {
            Optional<ContactEntity> existingContact = contactsRepository.findById(id);
            if (existingContact.isPresent())
            {
                List<String> wrongemails = Checkvalidation(contactDto);
                if (!wrongemails.isEmpty()) {
                    throw new IllegalArgumentException(wrongemails + " don't exist");
                }
                ContactEntity contact = contactDto.getContact();
                contact.setOwner(userRepository.findById(contactDto.getOwnerId()).get());
                contact.setEmailAddress(contactDto.getEmailAddress());
                return contactsRepository.save(contact).getcontactdto();
            }
            else{
                throw new IllegalArgumentException("this contact doesn't exist");
            }
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
    public List<String> Checkvalidation(ContactDto contactDto){
        List<String> wrongemailaddress= new ArrayList<>();
        for (String e:contactDto.getEmailAddress()){
            Optional<UserEntity> user = userRepository.findByEmailAddress(e);
            if(user.isEmpty())
                wrongemailaddress.add(e);
        }
        return  wrongemailaddress;
    }
}
