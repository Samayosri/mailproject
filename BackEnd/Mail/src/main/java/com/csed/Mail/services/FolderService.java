package com.csed.Mail.Services;

import com.csed.Mail.model.FolderEntity;
import com.csed.Mail.model.MailEntity;
import com.csed.Mail.model.UserEntity;

import java.util.List;

public interface FolderService {

    MailEntity sendMail(MailEntity mailEntity) throws IllegalArgumentException;

    MailEntity draftMail(MailEntity mailEntity) throws IllegalArgumentException;

    void validateReceivers(MailEntity mailEntity);

    void sendMailToReceivers(List<String> receivers, MailEntity mailEntity);

    void moveMailBetweenFoldersByName(MailEntity mailEntity, String fromFolder, String toFolder, UserEntity sender);

    void moveMailBetweenFoldersById(MailEntity mailEntity, Long fromFolder, Long toFolder);

    Long getFolderIdByName(String folderName, UserEntity user);

    FolderEntity getFolderById(Long folderId);

    void addEmailToFolderById(MailEntity mailEntity, Long folderId);

    void removeEmailFromFolderById(MailEntity mailEntity, Long folderId);

    void addEmailToFolderByName(MailEntity mailEntity, String folderName, UserEntity user);

    void removeEmailFromFolderByName(MailEntity mailEntity, String folderName, UserEntity user);
}


// move from to
// delete
// send
// draft

// deleted emails:
//      email id
//      user id
//      deletion date

// trash :
// when the user requested the trash folder:
// for each email in trash:
//          check if the deletion time has expired : delete it from trash folder and the table
//          else: send it