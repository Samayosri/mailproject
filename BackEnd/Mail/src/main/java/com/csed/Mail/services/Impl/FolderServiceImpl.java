package com.csed.Mail.Services.Impl;

import com.csed.Mail.Services.FolderService;
import com.csed.Mail.model.FolderEntity;
import com.csed.Mail.model.MailEntity;
import com.csed.Mail.model.UserEntity;
import com.csed.Mail.repositories.FolderRepository;
import com.csed.Mail.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class FolderServiceImpl implements FolderService {

    private final FolderRepository folderRepository;
    private final UserRepository userRepository;

    public FolderServiceImpl(FolderRepository folderRepository, UserRepository userRepository) {
        this.folderRepository = folderRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void sendMail(MailEntity mailEntity) throws Exception {
        // Move the mail to "Sent" and remove from "Drafts"
        moveToFolder(mailEntity, "Drafts", false);  // Remove from Drafts
        moveToFolder(mailEntity, "Sent", true);     // Add to Sent Folder

        processReceivers(mailEntity.getToReceivers(), mailEntity);
        processReceivers(mailEntity.getCcReceivers(), mailEntity);
        processReceivers(mailEntity.getBccReceivers(), mailEntity);
    }

    @Override
    public void draftMail(MailEntity mailEntity) throws Exception {
        moveToFolder(mailEntity, "Drafts", true);
    }

    private void processReceivers(Set<String> receivers, MailEntity mailEntity) throws Exception {
        for (String emailAddress : receivers) {
            Optional<UserEntity> receiver = userRepository.findByEmailAddress(emailAddress);
            if (receiver.isPresent()) {
                moveToFolder(mailEntity, "Inbox", true, receiver.get());
            } else {
                throw new Exception(emailAddress + " does not exist.");
            }
        }
    }

    private void moveToFolder(MailEntity mailEntity, String folderName, boolean add) {
        moveToFolder(mailEntity, folderName, add, mailEntity.getSender());
    }

    private void moveToFolder(MailEntity mailEntity, String folderName, boolean add, UserEntity user) {
        FolderEntity folder = folderRepository.findByOwnerAndName(user, folderName)
                .orElseGet(() -> createFolder(user, folderName));

        Set<MailEntity> emails = new HashSet<>(folder.getEmails());

        if (add) {
            emails.add(mailEntity);
        } else {
            emails.remove(mailEntity);
        }

        folder.setEmails(emails);
        folderRepository.save(folder);
    }


    private FolderEntity createFolder(UserEntity user, String folderName) {
        return folderRepository.save(
                FolderEntity.builder()
                        .owner(user)
                        .name(folderName)
                        .emails(new HashSet<>())
                        .build()
        );
    }
}
