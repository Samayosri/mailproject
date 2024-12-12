package com.csed.Mail.Services;

import com.csed.Mail.model.FolderEntity;
import com.csed.Mail.model.MailEntity;

public interface FolderService {

    void sendMail(MailEntity mailEntity) throws Exception;

    void draftMail(MailEntity mailEntity) throws Exception;
}

