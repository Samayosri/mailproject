package com.csed.Mail.commands;

import com.csed.Mail.Search.Impl.ReceiversEmailAddressCriteria;
import com.csed.Mail.model.Dtos.MailDto;
import com.csed.Mail.model.Dtos.MoveDto;
import com.csed.Mail.model.MailEntity;
import com.csed.Mail.model.UserEntity;
import com.csed.Mail.repositories.MailRepository;

import javax.sound.midi.Receiver;
import java.util.*;

public class Invoker {
    Command command;
    Queue<Command> commandList;
    private final CommandService commandService;
 private final MailRepository mailRepository;
    public Invoker(CommandService commandService,MailRepository mailRepository) {
        this.commandService = commandService;
        this.commandList = new LinkedList<>();
         this.mailRepository=mailRepository;
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
        if(command instanceof SendCommand ){
            MailEntity mailEntity = ((SendCommand) command).getMailEntity();
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
            mailEntity = mailRepository.save(mailEntity);
            SendCommand send = new SendCommand(commandService);
            send.setMailEntity(mailEntity);
            send.setReciever(String.valueOf(Receivers));
            commandList.add(send);
        }
        if(command instanceof DraftCommand ){
            MailEntity mailEntity = ((DraftCommand) command).getMailEntity();

            if (mailEntity.getId() == null) {
                mailEntity = mailRepository.save(mailEntity);
            } else {
                mailRepository.save(mailEntity);
            }

            DraftCommand draft = new DraftCommand(commandService);
            draft.setMailEntity(mailEntity);
            commandList.add(draft);
        }

    }
    public void executeCommand(){
        while (!commandList.isEmpty()){
            Command currentCommand = commandList.remove();
            currentCommand.execute();
        }
    }

}
