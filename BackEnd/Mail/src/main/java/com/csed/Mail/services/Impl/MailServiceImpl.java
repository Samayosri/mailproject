package com.csed.Mail.Services.Impl;

import com.csed.Mail.Services.MailService;
import com.csed.Mail.commands.*;
import com.csed.Mail.mappers.Mapper;
import com.csed.Mail.model.Dtos.MailDto;
import com.csed.Mail.model.Dtos.MoveDto;
import com.csed.Mail.model.FolderEntity;
import com.csed.Mail.model.MailEntity;
import com.csed.Mail.repositories.FolderRepository;
import com.csed.Mail.repositories.MailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class MailServiceImpl implements MailService {
    MailRepository mailRepository;
    FolderRepository folderRepository;
    private final CommandFactory commandFactory ;
    private final CommandService commandService;
    private final Mapper<MailEntity, MailDto> mailMapper;

    public MailServiceImpl(MailRepository mailRepository, FolderRepository folderRepository, CommandFactory commandFactory, CommandService commandService, Mapper<MailEntity, MailDto> mailMapper) {
        this.mailRepository = mailRepository;
        this.folderRepository = folderRepository;
        this.commandFactory = commandFactory;
        this.commandService = commandService;
        this.mailMapper = mailMapper;
    }

    @Override
    public void move(MoveDto moveDto){
        Invoker invoker = new Invoker(commandService);
        MoveCommand moveCommand = (MoveCommand) commandFactory.getCommand("move");
        moveCommand .setMoveDto(moveDto);
        invoker.setCommand(moveCommand);
        invoker.executeCommand();

    }
    @Override
    public void trash(MoveDto moveDto){
        Invoker invoker = new Invoker(commandService);
        TrashCommand trashCommand = (TrashCommand) commandFactory.getCommand("trash");
        trashCommand .setMoveDto(moveDto);
        invoker.setCommand(trashCommand);
        invoker.executeCommand();

    }


    @Override
    public List<MailDto> getListEmailsByFolderId(Long folderId) throws RuntimeException{
        Optional<FolderEntity> folder = folderRepository.findById(folderId);
        if(folder.isPresent()){
            return mailMapper.mapListToDto(folder.get().getEmails());
        }
        else {
            throw new RuntimeException("Folder does not exit");
        }
    }

    @Override
    public List<MailDto> sortDtoMailsByDate(List<MailDto> dtoMails) { // sorts descending
        if (dtoMails == null)
            return new ArrayList<>();
        return dtoMails.
                stream().
                sorted(
                        Comparator.
                                comparing(MailDto::getCreationDate).
                                reversed()
                ).
                collect(Collectors.toList());
    }

    @Override
    public List<MailDto> sortDtoMailsByImportance(List<MailDto> dtoMails) { // sorts descending
        if (dtoMails == null)
            return new ArrayList<>();
        return dtoMails.
                stream().
                sorted(
                        Comparator.
                                comparing(MailDto::getImportance).
                                thenComparing(MailDto::getCreationDate, Comparator.reverseOrder())
                ).
                collect(Collectors.toList());
    }

    @Override
    public List<MailDto> getPage(List<MailDto> mailDtos, int pageNumber, int pageSize) {
        int fromIndex = pageNumber * pageSize;
        if (fromIndex >= mailDtos.size()) {
            return new ArrayList<>();
        }

        int toIndex = Math.min(fromIndex + pageSize, mailDtos.size());

        return mailDtos.subList(fromIndex, toIndex);
    }
}
