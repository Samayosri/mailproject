package com.csed.Mail.Services.Impl;

import com.csed.Mail.Services.FolderService;
import com.csed.Mail.commands.*;
import com.csed.Mail.model.MailEntity;
import org.springframework.stereotype.Service;

@Service
public class FolderServiceImpl implements FolderService {

   private final CommandFactory commandFactory;
   private final CommandService commandService;

    public FolderServiceImpl(CommandFactory commandFactory, CommandService commandService) {
        this.commandFactory = commandFactory;
        this.commandService = commandService;
    }

    @Override
    public MailEntity sendMail(MailEntity mailEntity) throws IllegalArgumentException {
        Invoker invoker = new Invoker(commandService);
        SendCommand sendCommand = (SendCommand) commandFactory.getCommand("send");
        sendCommand.setMailEntity(mailEntity);
        invoker.setCommand(sendCommand);
        invoker.executeCommand();
        return null;
    }
    @Override
    public MailEntity draftMail(MailEntity mailEntity) {

        Invoker invoker = new Invoker(commandService);
        DraftCommand draftCommand = (DraftCommand) commandFactory.getCommand("draft");
        draftCommand.setMailEntity(mailEntity);
        invoker.setCommand(draftCommand);
        invoker.executeCommand();
        return null;
    }
}
