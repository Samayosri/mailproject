package com.csed.Mail.commands;

import com.csed.Mail.model.Dtos.DeleteDto;
import com.csed.Mail.model.Dtos.MoveDto;
import com.csed.Mail.model.FolderEntity;
import com.csed.Mail.model.MailEntity;
import com.csed.Mail.model.UserEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
public class TrashCommand implements Command{

    private final CommandService commandService;
    DeleteDto deleteDto;



    public TrashCommand(CommandService commandService) {
        this.commandService = commandService;
    }


    @Override
    public void execute() {
        //set delete date
        Set<MailEntity> selectedMails = new HashSet<>();
        for (Long id : deleteDto.getMailIds()) {
            selectedMails.add(commandService.getMailById(id));
        }
        UserEntity user = commandService.getUserById(deleteDto.getUserId());
        FolderEntity trash = null;
        for(FolderEntity folder : user.getFolders()){
            if(folder.getName().equals("Trash")){
                trash = folder;
                break;
            }
        }
        if(trash == null){
            throw new IllegalArgumentException("This nice user don't have trash");
        }
        for(MailEntity mailEntity : selectedMails){
            mailEntity.setFolder(trash);
            //commandService.moveMailToTrash(mailEntity.getId());
            commandService.saveMail(mailEntity);
        }



    }
}
