package com.csed.Mail.commands;

import com.csed.Mail.model.DeletedMailEntity;
import com.csed.Mail.model.Dtos.MailDto;
import com.csed.Mail.model.FolderEntity;
import com.csed.Mail.model.MailEntity;
import com.csed.Mail.model.UserEntity;
import com.csed.Mail.repositories.DeletedMailsRepository;
import com.csed.Mail.repositories.FolderRepository;
import com.csed.Mail.repositories.MailRepository;
import com.csed.Mail.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CommandService {

    private final FolderRepository folderRepository;
    private final UserRepository userRepository;
    private final MailRepository mailRepository;
    private final DeletedMailsRepository deletedMailsRepository;

    public CommandService(FolderRepository folderRepository, UserRepository userRepository, MailRepository mailRepository, DeletedMailsRepository deletedMailsRepository) {
        this.folderRepository = folderRepository;
        this.userRepository = userRepository;
        this.mailRepository = mailRepository;
        this.deletedMailsRepository = deletedMailsRepository;
    }

    public UserEntity getUserById(Long id){
        return userRepository.findById(id).orElseThrow( () -> new IllegalArgumentException("User not Found"));
    }
    public MailEntity getMailById(Long id){
        return mailRepository.findById(id).orElseThrow( () -> new IllegalArgumentException("Mail not Found"));
    }
    public FolderEntity saveFolder(FolderEntity folder){
        return folderRepository.save(folder);
    }

    public FolderEntity getFolder(Long id){
        return folderRepository.findById(id).orElseThrow( () -> new IllegalArgumentException("Folder not Found"));
    }


    public void saveUser(UserEntity user){
        userRepository.save(user);
    }
    public MailEntity saveMail(MailEntity mail){
       return mailRepository.save(mail);
    }



    public void checkReceivers(List<String> receivers) {
        if(receivers == null) return;
        for (String emailAddress : receivers) {
            if (userRepository.findByEmailAddress(emailAddress).isEmpty()) {
                throw new IllegalArgumentException( emailAddress + "' does not exist.");
            }
        }
    }
    public MailEntity draftMail(MailEntity mailEntity) {
        if (mailEntity.getId() == null) {
            mailEntity = mailRepository.save(mailEntity);
            addEmailToFolderByName(mailEntity, "Drafts", mailEntity.getSender());
        } else {
            mailEntity = mailRepository.save(mailEntity);
        }
        return mailEntity;
    }

    public void removeEmailFromFolderByName(MailEntity mailEntity, String folderName, UserEntity user) {
        List<FolderEntity> folders = user.getFolders();
        FolderEntity folder = null;
        for(FolderEntity f : folders){
            if(f.getName().equals(folderName)){
                folder = f;
                break;
            }
        }
        if(folder == null) {
            throw new IllegalArgumentException("folder not found");
        }
        folder.getEmails().remove(mailEntity);
        folderRepository.save(folder);

    }


    public void addEmailToFolderByName(MailEntity mailEntity, String folderName, UserEntity user) {
        List<FolderEntity> folders = user.getFolders();
        FolderEntity folder = null;
        for(FolderEntity f : folders){
            if(f.getName().equals(folderName)){
                folder = f;
                break;
            }
        }
        if(folder == null) {
            throw new IllegalArgumentException("folder not found");
        }
        mailEntity.setFolder(folder);
        mailRepository.save(mailEntity);
    }
    public void sendMailToReceivers(String emailAddress, MailEntity mailEntity) {
        Optional<UserEntity> receiver = userRepository.findByEmailAddress(emailAddress);
        addEmailToFolderByName(mailEntity, "Inbox", receiver.get());
    }

    public MailEntity sendMail(MailEntity mailEntity,String Reciever) throws IllegalArgumentException {

        sendMailToReceivers(Reciever, mailEntity);

        return mailEntity;
    }

    public void moveMailToTrash(Long folderId, Long mailId) {
        DeletedMailEntity deletedMailEntity = DeletedMailEntity.builder()
                .folderId(folderId)
                .mailId(mailId)
                .build();
        this.deletedMailsRepository.save(deletedMailEntity);
    }

    public void removeMailFromTrash(Long mailId) {
        this.deletedMailsRepository.deleteByMailId(mailId);
    }

    public void filterDeletedMailsFromTrashFolder(Long folderId) {
        List<DeletedMailEntity> deletedMailEntities = this.deletedMailsRepository.findAllByFolderId(folderId);

        FolderEntity folderEntity = getFolder(folderId);
        List<MailEntity> mailEntities = folderEntity.getEmails();

        LocalDateTime thresholdDate = LocalDateTime.now().minusDays(30);

        List<Long> mailIdsToDelete = new ArrayList<>();
        for (DeletedMailEntity deletedMail : deletedMailEntities) {
            if (deletedMail.getDeletionTime().isBefore(thresholdDate)) {
                mailIdsToDelete.add(deletedMail.getMailId());
            }
        }

        mailEntities.removeIf(mail -> mailIdsToDelete.contains(mail.getId()));

        this.mailRepository.deleteAllById(mailIdsToDelete);

        this.deletedMailsRepository.deleteAllByMailIdIn(mailIdsToDelete);

        folderEntity.setEmails(mailEntities);

        this.folderRepository.save(folderEntity);
    }
}