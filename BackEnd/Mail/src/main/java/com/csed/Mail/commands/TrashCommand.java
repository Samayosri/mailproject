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
public class TrashCommand extends Command{

    private final CommandService commandService;
    private Long sourceFolderId;
    private Long destinationFolderId;
    private UserEntity user;
    private MailEntity mail;

    public TrashCommand(CommandService commandService) {
        this.commandService = commandService;
    }


    @Override
    public void execute() {
        //set delete date
        List<FolderEntity> folders = user.getFolders();
        for(FolderEntity folder : folders){
            if(folder.getId().equals(sourceFolderId)) {
                folder.getEmails().remove(mail);
                commandService.saveFolder(folder);
            }
            else if(folder.getId().equals(destinationFolderId)){
                folder.getEmails().add(mail);
                commandService.saveFolder(folder);
            }
        }

    }
}
