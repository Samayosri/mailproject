package com.csed.Mail.Services.Impl;

import com.csed.Mail.MailApplication;
import com.csed.Mail.Services.FolderService;
import com.csed.Mail.model.FolderEntity;
import com.csed.Mail.model.MailEntity;
import com.csed.Mail.model.UserEntity;
import com.csed.Mail.repositories.FolderRepository;
import com.csed.Mail.repositories.MailRepository;
import com.csed.Mail.repositories.UserRepository;
import jakarta.persistence.EntityManager;
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

        //check for recivers if any one of them found put mail in drafts and throw exeption
        // and pass mail dto
         mailEntity = mailRepository.save(mailEntity);
        // Move the mail to "Sent" and remove from "Drafts"
        moveToFolder(mailEntity, "Drafts", false);  // Remove from Drafts
        moveToFolder(mailEntity, "Sent", true);     // Add to Sent Folder

        processReceivers(mailEntity.getToReceivers(), mailEntity);
        processReceivers(mailEntity.getCcReceivers(), mailEntity);
        processReceivers(mailEntity.getBccReceivers(), mailEntity);
    }

    @Override
    public void draftMail(MailEntity mailEntity) throws IllegalArgumentException {
        moveToFolder(mailEntity, "Drafts", true);
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

    private void moveToFolder(MailEntity mailEntity, String folderName, boolean add) {
        moveToFolder(mailEntity, folderName, add, mailEntity.getSender());
    }

    private void moveToFolder(MailEntity mailEntity, String folderName, boolean add, UserEntity user) {
        Optional<FolderEntity> folder = folderRepository.findByOwnerAndName(user, folderName);

        if (add) {
            folder.get().getEmails().add(mailEntity);
        } else {
           folder.get().getEmails().remove(mailEntity);
        }
        folderRepository.save(folder.get());
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
