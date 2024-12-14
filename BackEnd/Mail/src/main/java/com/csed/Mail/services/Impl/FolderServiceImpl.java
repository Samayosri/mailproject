package com.csed.Mail.Services.Impl;

import com.csed.Mail.MailApplication;
import com.csed.Mail.Services.FolderService;
import com.csed.Mail.model.FolderEntity;
import com.csed.Mail.model.MailEntity;
import com.csed.Mail.model.UserEntity;
import com.csed.Mail.repositories.FolderRepository;
import com.csed.Mail.repositories.MailRepository;
import com.csed.Mail.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FolderServiceImpl implements FolderService {

    private final FolderRepository folderRepository;
    private final UserRepository userRepository;
    private final MailRepository mailRepository;

    public FolderServiceImpl(FolderRepository folderRepository, UserRepository userRepository, MailRepository mailRepository) {
        this.folderRepository = folderRepository;
        this.userRepository = userRepository;
        this.mailRepository = mailRepository;
    }

    @Override
    public void sendMail(MailEntity mailEntity) throws IllegalArgumentException {

        checkReceivers(mailEntity.getToReceivers());
        checkReceivers(mailEntity.getBccReceivers());
        checkReceivers(mailEntity.getCcReceivers());

         mailEntity = mailRepository.save(mailEntity);
        // Move the mail to "Sent" and remove from "Drafts"
        moveToFolder(mailEntity, "Drafts", false);  // Remove from Drafts
        List<String> hideBcc = mailEntity.getBccReceivers(); // remove bcc from mail entity
        mailEntity.setBccReceivers(null);
        moveToFolder(mailEntity, "Sent", true);     // Add to Sent Folder

        processReceivers(mailEntity.getToReceivers(), mailEntity);
        processReceivers(mailEntity.getCcReceivers(), mailEntity);
        processReceivers(hideBcc, mailEntity);
        // tested sending to multible recivers and send to wrong mail
        // and adding to draft and edting it
        // try to add mail to draft then send it it removed succcesfully nice
    }

    @Override
    public void draftMail(MailEntity mailEntity) throws IllegalArgumentException {
        if(mailEntity.getId() == null){ // create new mail by butting in draft
            moveToFolder(mailEntity, "Drafts", true);
        }
        else{ // mail exist only need update it
            mailRepository.save(mailEntity);
        }

    }

    private void processReceivers(List<String> receivers, MailEntity mailEntity) throws IllegalArgumentException {
        for (String emailAddress : receivers) {
            Optional<UserEntity> receiver = userRepository.findByEmailAddress(emailAddress);
            if (receiver.isPresent()) {
                moveToFolder(mailEntity, "Inbox", true, receiver.get());
            } else {
                throw new IllegalArgumentException(emailAddress + " does not exist.");
            }
        }
    }
    private void checkReceivers(List<String> receivers) throws IllegalArgumentException {
        for (String emailAddress : receivers) {
            Optional<UserEntity> receiver = userRepository.findByEmailAddress(emailAddress);
            if (receiver.isEmpty()) {
                throw new IllegalArgumentException(emailAddress + " does not exist.");
            }
        }
    }

    private void moveToFolder(MailEntity mailEntity, String folderName, boolean add) {
        moveToFolder(mailEntity, folderName, add, mailEntity.getSender());
    }

    private void moveToFolder(MailEntity mailEntity, String folderName, boolean add, UserEntity user) {
        List<FolderEntity> folders = user.getFolders();
        FolderEntity folder = null;
        for(FolderEntity f : folders){
            if(f.getName().equals(folderName)){
                folder = f;
                break;
            }
        }
        if(folder == null){
            throw new IllegalArgumentException("folder not found");
        }

        if (add) {
            folder.getEmails().add(mailEntity);
        } else {
           folder.getEmails().remove(mailEntity);
        }
        folderRepository.save(folder);
    }


    private FolderEntity createFolder(UserEntity user, String folderName) {
        return folderRepository.save(
                FolderEntity.builder()
                        .owner(user)
                        .name(folderName)
                        .emails(new ArrayList<>())
                        .build()
        );
    }
}
