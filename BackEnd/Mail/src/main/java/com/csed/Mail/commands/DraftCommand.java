package com.csed.Mail.commands;

import com.csed.Mail.model.MailEntity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DraftCommand implements Command{

    MailEntity mailEntity;

    private final CommandService commandService;

    public DraftCommand(CommandService commandService) {
        this.commandService = commandService;
    }
    @Override
    public void execute() {
        commandService.addEmailToFolderByName(mailEntity, "Drafts", mailEntity.getSender());
    }
}
