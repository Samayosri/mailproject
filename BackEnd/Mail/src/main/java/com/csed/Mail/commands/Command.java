package com.csed.Mail.commands;

import com.csed.Mail.model.Dtos.MoveDto;
import com.csed.Mail.model.MailEntity;
import lombok.Getter;
import lombok.Setter;

public interface Command {

    abstract void execute() throws IllegalArgumentException;
}
