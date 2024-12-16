package com.csed.Mail.commands;

import com.csed.Mail.model.Dtos.MoveDto;
import com.csed.Mail.model.FolderEntity;
import com.csed.Mail.model.MailEntity;
import com.csed.Mail.model.UserEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class MoveCommand implements Command{
    MoveDto moveDto;
    Long fromId;
    Long toId;
    UserEntity user;
    MailEntity mail;
    private final CommandService commandService;

    public MoveCommand(CommandService commandService) {
        this.commandService = commandService;
    }

    @Override
    public void execute() {
        List<FolderEntity> folders = user.getFolders();
        for(FolderEntity folder : folders){
            if(folder.getId().equals(fromId)){
                folders.remove(folder);
            }
            else if(folder.getId().equals(toId)){
                folders.add(folder);
            }
        }
        commandService.saveUser(user);
    }
}
