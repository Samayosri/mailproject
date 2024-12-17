package com.csed.Mail.commands;

import com.csed.Mail.model.MailEntity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SendCommand implements Command{
    String recevier;
    MailEntity mailEntity;
    private final CommandService commandService;

    public SendCommand(CommandService commandService) {
        this.commandService = commandService;
    }

    @Override
    public void execute() {

        commandService.sendMailToReceivers(recevier, mailEntity);
    }


}
