package com.csed.Mail.Services.Impl;

import com.csed.Mail.Services.FolderService;
import com.csed.Mail.model.FolderEntity;
import com.csed.Mail.model.MailEntity;
import com.csed.Mail.model.UserEntity;
import com.csed.Mail.repositories.FolderRepository;
import com.csed.Mail.repositories.UserRepository;
import java.util.HashSet;
import java.util.Optional;

public class FolderServiceImpl implements FolderService {
    private final FolderRepository folderRepository;
    private final UserRepository userRepository;

    public FolderServiceImpl(FolderRepository folderRepository, UserRepository userRepository) {
        this.folderRepository = folderRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void sendMail(MailEntity mailEntity) {
        // Remove from Drafts
        Optional<FolderEntity> draftFolder = folderRepository.findByOwnerAndName(mailEntity.getSender(), "Drafts");
        if(draftFolder.isEmpty()) {
            FolderEntity draft = createFolder(mailEntity.getSender(), "Drafts");
            folderRepository.save(draft);
        } else {
            draftFolder.ifPresent(folderEntity -> folderEntity.getEmails().remove(mailEntity));
        }

        // Add to Sent folder
        Optional<FolderEntity> sentFolder = folderRepository.findByOwnerAndName(mailEntity.getSender(), "Sent");
        if (sentFolder.isEmpty()) {
            FolderEntity sent = createFolder(mailEntity.getSender(), "Sent");
            sent.getEmails().add(mailEntity);
            folderRepository.save(sent);
        } else {
            sentFolder.get().getEmails().add(mailEntity);
            folderRepository.save(sentFolder.get());
        }

        // Add to Receivers' Inboxes
        for (String emailAddress : mailEntity.getToReceivers()) {
            processReceiverInbox(emailAddress, mailEntity);
        }
        for (String emailAddress : mailEntity.getCcReceivers()) {
            processReceiverInbox(emailAddress, mailEntity);
        }
        for (String emailAddress : mailEntity.getBccReceivers()) {
            processReceiverInbox(emailAddress, mailEntity);
        }
    }

    private void processReceiverInbox(String emailAddress, MailEntity mailEntity) {
        Optional<UserEntity> receiver = userRepository.findByEmailAddress(emailAddress);
        if (receiver.isPresent()) {
            Optional<FolderEntity> inboxFolder = folderRepository.findByOwnerAndName(receiver.get(), "Inbox");
            if (inboxFolder.isEmpty()) {
                FolderEntity inbox = createFolder(receiver.get(), "Inbox");
                inbox.getEmails().add(mailEntity);
                folderRepository.save(inbox);
            } else {
                inboxFolder.get().getEmails().add(mailEntity);
                folderRepository.save(inboxFolder.get());
            }
        }
    }

    private FolderEntity createFolder(UserEntity user, String folderName) {
        FolderEntity folder = FolderEntity.builder()
                .owner(user)
                .name(folderName)
                .emails(new HashSet<>())
                .build();
        folderRepository.save(folder);
        return folder;
    }
}
