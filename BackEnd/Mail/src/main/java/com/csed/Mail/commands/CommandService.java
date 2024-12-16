package com.csed.Mail.commands;

import com.csed.Mail.model.FolderEntity;
import com.csed.Mail.model.MailEntity;
import com.csed.Mail.model.UserEntity;
import com.csed.Mail.repositories.FolderRepository;
import com.csed.Mail.repositories.MailRepository;
import com.csed.Mail.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public UserEntity getUserById(Long id){
        return userRepository.findById(id).orElseThrow( () -> new IllegalArgumentException("User not Found"));
    }
    public MailEntity getMailById(Long id){
        return mailRepository.findById(id).orElseThrow( () -> new IllegalArgumentException("Mail not Found"));
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
        folder.getEmails().add(mailEntity);
        folderRepository.save(folder);
    }
    public void sendMailToReceivers(String emailAddress, MailEntity mailEntity) {
        Optional<UserEntity> receiver = userRepository.findByEmailAddress(emailAddress);
        addEmailToFolderByName(mailEntity, "Inbox", receiver.get());
    }

    public MailEntity sendMail(MailEntity mailEntity,String Reciever) throws IllegalArgumentException {

        sendMailToReceivers(Reciever, mailEntity);

        return mailEntity;
    }


}