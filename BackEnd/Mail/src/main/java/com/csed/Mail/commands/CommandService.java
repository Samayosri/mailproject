package com.csed.Mail.commands;

import com.csed.Mail.mappers.impl.MailMapperImpl;
import com.csed.Mail.model.*;
import com.csed.Mail.repositories.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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
    private final AttachmentRepository attachmentRepository;
    private final SimpMessagingTemplate messagingTemplate;
    private final MailMapperImpl mailMapper ;

    public CommandService(FolderRepository folderRepository, UserRepository userRepository, MailRepository mailRepository, DeletedMailsRepository deletedMailsRepository, AttachmentRepository attachmentRepository, SimpMessagingTemplate messagingTemplate, MailMapperImpl mailMapper) {
        this.folderRepository = folderRepository;
        this.userRepository = userRepository;
        this.mailRepository = mailRepository;
        this.deletedMailsRepository = deletedMailsRepository;
        this.attachmentRepository = attachmentRepository;
        this.messagingTemplate = messagingTemplate;
        this.mailMapper = mailMapper;
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
        mailEntity.setState("Draft");
        if (mailEntity.getId() == null) {
            mailEntity = mailRepository.save(mailEntity);
            addEmailToFolderByName(mailEntity, "Drafts", mailEntity.getSender());
        } else {
            mailEntity.setFolder(getFolderByName("Drafts",mailEntity.getSender()));
            mailEntity = mailRepository.save(mailEntity);
        }
        return mailEntity;
    }
    public FolderEntity getFolderByName( String folderName, UserEntity user) {
        List<FolderEntity> folders = user.getFolders();
        FolderEntity folder = null;
        for (FolderEntity f : folders) {
            if (f.getName().equals(folderName)) {
                folder = f;
                break;
            }
        }
        if (folder == null) {
            throw new IllegalArgumentException("folder not found");
        }
        return folder;
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
        saveMail(mailEntity);
        addEmailToFolderByName(mailEntity, "Inbox", receiver.get());
        messagingTemplate.convertAndSend("/send/inbox/"+receiver.get().getId(),mailMapper.mapToDto(mailEntity));


    }


    public void moveMailToTrash(Long mailId) {
        DeletedMailEntity deletedMailEntity = DeletedMailEntity.builder()
                .mail(mailId)
                .build();
        this.deletedMailsRepository.save(deletedMailEntity);
    }

    public void removeMailFromTrash(Long mailId) {
        deletedMailsRepository.deleteByMail(mailId);
    }

    public void filterDeletedMailsFromTrashFolder() {
        List<DeletedMailEntity> deletedMailEntities = this.deletedMailsRepository.findAll();
        LocalDateTime thresholdDate = LocalDateTime.now().minusDays(30);
        List<Long> mailIdsToDelete = new ArrayList<>();
        for (DeletedMailEntity deletedMail : deletedMailEntities) {
            if (deletedMail.getDeletionTime().isBefore(thresholdDate)) {
                mailIdsToDelete.add(deletedMail.getMail());
                deletedMailsRepository.delete(deletedMail);
            }
        }
        for(Long id : mailIdsToDelete){
            MailEntity mail = getMailById(id);
            FolderEntity folder = mail.getFolder();
            folder.getEmails().remove(mail);
            folderRepository.save(folder);
            mailRepository.delete(mail);
        }

    }
    public AttachmentEntity getAttch(Long id){
        return attachmentRepository.findById(id).orElseThrow(()->new IllegalArgumentException("att not found"));
    }
     public List<Long> attachmentsIds (List<Long> ids){
        List<Long> copied = new ArrayList<>();
        for(long id : ids) {
            AttachmentEntity old = getAttch(id);
            AttachmentEntity copy = old.clone();
             copy = attachmentRepository.save(copy);
            copied.add(copy.getId());

        }
        return copied;
     }

}