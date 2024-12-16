package com.csed.Mail.commands;

import com.csed.Mail.model.FolderEntity;
import com.csed.Mail.model.MailEntity;
import com.csed.Mail.model.UserEntity;
import com.csed.Mail.repositories.FolderRepository;
import com.csed.Mail.repositories.MailRepository;
import com.csed.Mail.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommandService {

    private final FolderRepository folderRepository;
    private final UserRepository userRepository;
    private final MailRepository mailRepository;

    public CommandService(FolderRepository folderRepository, UserRepository userRepository, MailRepository mailRepository) {
        this.folderRepository = folderRepository;
        this.userRepository = userRepository;
        this.mailRepository = mailRepository;
    }

//    public void moveMailBetweenFoldersById(MailEntity mailEntity, Long fromFolder, Long toFolder) {
//        removeEmailFromFolderById(mailEntity, fromFolder);
//        addEmailToFolderById(mailEntity, toFolder);
//    }
//    public FolderEntity getFolderById(Long folderId) {
//        return folderRepository.findById(folderId)
//                .orElseThrow(() -> new IllegalArgumentException("Folder with ID '" + folderId + "' not found."));
//    }
//    public void addEmailToFolderById(MailEntity mailEntity, Long folderId) {
//        FolderEntity folder = getFolderById(folderId);
//        folder.getEmails().add(mailEntity);
//        folderRepository.save(folder);
//    }
//    public void removeEmailFromFolderById(MailEntity mailEntity, Long folderId) {
//        FolderEntity folder = getFolderById(folderId);
//        folder.getEmails().remove(mailEntity);
//        folderRepository.save(folder);
//    }
    public UserEntity getUserById(Long id){
        return userRepository.findById(id).orElseThrow( () -> new IllegalArgumentException("User not Found"));
    }
    public MailEntity getMailById(Long id){
        return mailRepository.findById(id).orElseThrow( () -> new IllegalArgumentException("Mail not Found"));
    }

    public void saveUser(UserEntity user){
        userRepository.save(user);
    }

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



}
