package com.csed.Mail.services;

import com.csed.Mail.model.FolderEntity;
import com.csed.Mail.model.MailEntity;

public interface FolderService {

    FolderEntity sendMail(MailEntity mailEntity);

}
