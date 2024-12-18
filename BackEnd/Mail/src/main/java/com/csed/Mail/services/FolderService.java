package com.csed.Mail.Services;

import com.csed.Mail.model.FolderEntity;
import com.csed.Mail.model.MailEntity;
import com.csed.Mail.model.UserEntity;

import java.util.List;

public interface FolderService {

    MailEntity sendMail(MailEntity mailEntity) throws IllegalArgumentException;

    MailEntity draftMail(MailEntity mailEntity) throws IllegalArgumentException;

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