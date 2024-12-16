package com.csed.Mail.commands;

import com.csed.Mail.model.MailEntity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SendCommand extends Command{
    String reciever;
    private final CommandService commandService;

    public SendCommand(CommandService commandService) {
        this.commandService = commandService;
    }

    @Override
    public void execute() {
        commandService.removeEmailFromFolderByName(mailEntity,"Drafts",mailEntity.getSender());
     commandService.sendMailToReceivers(reciever, mailEntity);
     commandService.addEmailToFolderByName(mailEntity,"Sent",mailEntity.getSender());
    }


}
