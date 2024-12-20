package com.csed.Mail.commands;

import com.csed.Mail.model.MailEntity;
import org.springframework.stereotype.Component;

import java.util.*;


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
            this.command = command;
            commandList.add(command);
        }
        if(command instanceof SendCommand ){
            MailEntity mailEntity =  ((SendCommand) command).getMailEntity();
            List<String> Receivers = new ArrayList<>();
            if (mailEntity.getCcReceivers() != null) {
                Receivers.addAll(mailEntity.getCcReceivers());
            }
            if (mailEntity.getToReceivers() != null) {
                Receivers.addAll(mailEntity.getToReceivers());
            }
            if (mailEntity.getBccReceivers() != null) {
                Receivers.addAll(mailEntity.getBccReceivers());
            }
            commandService.checkReceivers(Receivers);
            mailEntity.setBccReceivers(null);
            mailEntity.setState("Sent");
            commandService.removeEmailFromFolderByName(mailEntity,"Drafts",mailEntity.getSender());
            commandService.saveMail(mailEntity);
            commandService.addEmailToFolderByName(mailEntity,"Sent",mailEntity.getSender());
            for(String receiver : Receivers){
                SendCommand send = new SendCommand(commandService);
                List<Long> clonedAttchment = commandService.attachmentsIds(mailEntity.getAttachments());
                MailEntity cloned = mailEntity.clone();
                cloned.setAttachments(clonedAttchment);
                send.setMailEntity(cloned);
                send.setRecevier(receiver);
                commandList.add(send);
            }

        }
        if(command instanceof DraftCommand ){
            commandList.add(command);
        }

    }
    public void executeCommand(){
        while (!commandList.isEmpty()){
            Command currentCommand = commandList.remove();
            currentCommand.execute();
        }
    }

}
