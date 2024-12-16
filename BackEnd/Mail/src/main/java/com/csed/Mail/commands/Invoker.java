package com.csed.Mail.commands;

import com.csed.Mail.model.Dtos.MoveDto;
import com.csed.Mail.model.MailEntity;
import com.csed.Mail.model.UserEntity;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Queue;

public class Invoker {
    Command command;
    Queue<Command> commandList;
    private final CommandService commandService;

    public Invoker(CommandService commandService) {
        this.commandService = commandService;
        this.commandList = new LinkedList<>();
    }

    public void setCommand(Command command){
        if(command instanceof MoveCommand || command instanceof TrashCommand){

            MoveDto moveDto = command instanceof MoveCommand ?
                    ((MoveCommand) command).getMoveDto():
                    ((TrashCommand) command).getMoveDto();

            for(Long mailId : moveDto.getMailIds()) {
                MailEntity mail = commandService.getMailById(mailId);
                Long userId = moveDto.getUserId();
                UserEntity user = commandService.getUserById(userId);
                if(command instanceof MoveCommand){
                    MoveCommand move = new MoveCommand(commandService);
                    move.setMail(mail);
                    move.setUser(user);
                    move.setDestinationFolderId(moveDto.getDestinationFolderId());
                    move.setSourceFolderId(moveDto.getSourceFolderId());
                    commandList.add(move);
                }
                else{
                    TrashCommand trash = new TrashCommand(commandService);
                    trash.setMail(mail);
                    trash.setUser(user);
                    trash.setDestinationFolderId(moveDto.getDestinationFolderId());
                    trash.setSourceFolderId(moveDto.getSourceFolderId());
                    commandList.add(trash);

                }

            }
        }
        if(command instanceof SendCommand || command instanceof TrashCommand){

//            MoveDto moveDto = command instanceof MoveCommand ?
//                    ((MoveCommand) command).getMoveDto():
//                    ((TrashCommand) command).getMoveDto();


        }
    }
    public void executeCommand(){
        while (!commandList.isEmpty()){
            Command currentCommand = commandList.remove();
            currentCommand.execute();
        }
    }

}
