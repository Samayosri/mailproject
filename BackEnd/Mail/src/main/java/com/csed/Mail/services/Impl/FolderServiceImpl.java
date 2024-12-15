package com.csed.Mail.Services.Impl;

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
    public MailEntity sendMail(MailEntity mailEntity) throws IllegalArgumentException {
        validateReceivers(mailEntity);

        mailEntity = mailRepository.save(mailEntity);

        List<String> bccReceivers = new ArrayList<>(mailEntity.getBccReceivers());
        mailEntity.setBccReceivers(null);

        moveMailBetweenFoldersByName(mailEntity, "Drafts", "Sent", mailEntity.getSender());

        sendMailToReceivers(mailEntity.getToReceivers(), mailEntity);
        sendMailToReceivers(mailEntity.getCcReceivers(), mailEntity);
        sendMailToReceivers(bccReceivers, mailEntity);

        return mailEntity;
    }

    @Override
    public MailEntity draftMail(MailEntity mailEntity) {
        if (mailEntity.getId() == null) {
            mailEntity = mailRepository.save(mailEntity);
            addEmailToFolderByName(mailEntity, "Drafts", mailEntity.getSender());
        } else {
            mailEntity = mailRepository.save(mailEntity);
        }
        return mailEntity;
    }

    @Override
    public void validateReceivers(MailEntity mailEntity) {
        checkReceivers(mailEntity.getToReceivers(), "To");
        checkReceivers(mailEntity.getCcReceivers(), "CC");
        checkReceivers(mailEntity.getBccReceivers(), "BCC");
    }

    private void checkReceivers(List<String> receivers, String type) {
        if(receivers == null) return;
        for (String emailAddress : receivers) {
            if (userRepository.findByEmailAddress(emailAddress).isEmpty()) {
                throw new IllegalArgumentException(type + " receiver '" + emailAddress + "' does not exist.");
            }
        }
    }

    @Override
    public void sendMailToReceivers(List<String> receivers, MailEntity mailEntity) {
        for (String emailAddress : receivers) {
            Optional<UserEntity> receiver = userRepository.findByEmailAddress(emailAddress);
            if (receiver.isPresent()) {
                addEmailToFolderByName(mailEntity, "Inbox", receiver.get());
            } else {
                throw new IllegalArgumentException(emailAddress + " does not exist.");
            }
        }
    }

    @Override
    public Long getFolderIdByName(String folderName, UserEntity user) {
        List<FolderEntity> folders = user.getFolders();
        for (FolderEntity folder : folders) {
            if (folder.getName().equals(folderName)) {
                return folder.getId();
            }
        }
        throw new IllegalArgumentException("Folder { " + folderName + " } not found");
    }
    @Override
    public void moveMailBetweenFoldersByName(MailEntity mailEntity, String fromFolder, String toFolder, UserEntity sender) {
        removeEmailFromFolderByName(mailEntity, fromFolder, sender);
        addEmailToFolderByName(mailEntity, toFolder, sender);
    }

    @Override
    public void moveMailBetweenFoldersById(MailEntity mailEntity, Long fromFolder, Long toFolder) {
        removeEmailFromFolderById(mailEntity, fromFolder);
        addEmailToFolderById(mailEntity, toFolder);
    }


    @Override
    public FolderEntity getFolderById(Long folderId) {
        return folderRepository.findById(folderId)
                .orElseThrow(() -> new IllegalArgumentException("Folder with ID '" + folderId + "' not found."));
    }

    @Override
    public void addEmailToFolderById(MailEntity mailEntity, Long folderId) {
        FolderEntity folder = getFolderById(folderId);
        folder.getEmails().add(mailEntity);
        folderRepository.save(folder);
    }

    @Override
    public void removeEmailFromFolderById(MailEntity mailEntity, Long folderId) {
        FolderEntity folder = getFolderById(folderId);
        folder.getEmails().remove(mailEntity);
        folderRepository.save(folder);
    }

    @Override
    public void addEmailToFolderByName(MailEntity mailEntity, String folderName, UserEntity user) {
        Long folderId = getFolderIdByName(folderName, user);
        addEmailToFolderById(mailEntity, folderId);
    }

    @Override
    public void removeEmailFromFolderByName(MailEntity mailEntity, String folderName, UserEntity user) {
        Long folderId = getFolderIdByName(folderName, user);
        removeEmailFromFolderById(mailEntity, folderId);
    }
}
