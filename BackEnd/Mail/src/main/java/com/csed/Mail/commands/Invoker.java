package com.csed.Mail.commands;

import com.csed.Mail.model.MailEntity;

import java.util.ArrayList;
import java.util.List;

public class Invoker {
    Command command;
    List<Command> commandList = new ArrayList<>();
    private final CommandService commandService;

    public Invoker(CommandService commandService) {
        this.commandService = commandService;
    }

    public void setCommand(Command command){
        if(command instanceof MoveCommand){
            for(Long mailId : ((MoveCommand) command).moveDto.getMailIds()) {
                MailEntity mail = commandService.getMailById(mailId);
                
            }


        }
    }
    public void executeCommand(){

    }

}
