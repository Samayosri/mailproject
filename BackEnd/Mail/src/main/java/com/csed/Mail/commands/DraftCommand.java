package com.csed.Mail.commands;

import com.csed.Mail.model.MailEntity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DraftCommand implements Command{

    private final CommandService commandService;
    MailEntity mailEntity;

    public DraftCommand(CommandService commandService) {
        this.commandService = commandService;
    }
    @Override
    public void execute() {
        commandService.draftMail(mailEntity);
    }
}
