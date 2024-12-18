package com.csed.Mail.commands;

import com.csed.Mail.model.Dtos.MoveDto;
import com.csed.Mail.model.FolderEntity;
import com.csed.Mail.model.MailEntity;
import com.csed.Mail.model.UserEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Getter
@Setter
public class MoveCommand implements Command {
    private final CommandService commandService;
    MoveDto moveDto;

    public MoveCommand(CommandService commandService) {
        this.commandService = commandService;
    }

    @Override
    public void execute() {
            Set<MailEntity> selectedMails = new HashSet<>();
            for (Long id : moveDto.getMailIds()) {
                selectedMails.add(commandService.getMailById(id));
            }
            FolderEntity folder = commandService.getFolder(moveDto.getDestinationFolderId());
            for(MailEntity mailEntity : selectedMails){
                if(mailEntity.getFolder().getName().equals("Drafts")){
                    continue;
                }
//                if(mailEntity.getFolder().equals("Trash")){
//                   commandService.removeMailFromTrash(mailEntity.getId());
//                }
                mailEntity.setFolder(folder);
                commandService.saveMail(mailEntity);
            }
    }
}
