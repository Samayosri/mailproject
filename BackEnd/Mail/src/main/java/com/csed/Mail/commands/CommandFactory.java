package com.csed.Mail.commands;

public class CommandFactory {
    private final CommandService commandService;

    public CommandFactory(CommandService commandService) {
        this.commandService = commandService;
    }

    public Command getCommand(String commandType){
        return switch (commandType) {
            case "send" -> new SendCommand(commandService);
            case "draft" -> new DraftCommand(commandService);
            case "move" -> new MoveCommand(commandService);
            case "trash" -> new TrashCommand(commandService);
            default -> null;
        };
    }

}
