package com.csed.Mail.commands;

import com.csed.Mail.model.Dtos.MoveDto;
import com.csed.Mail.model.FolderEntity;
import com.csed.Mail.model.MailEntity;
import com.csed.Mail.model.UserEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Builder
public class TrashCommand implements Command{

    MoveDto moveDto;
    private final CommandService commandService;
    Long fromId;
    Long toId;
    UserEntity user;
    MailEntity mail;

    public TrashCommand(CommandService commandService) {
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
        // set delete date for mail entity
        commandService.saveUser(user);



    }
}
