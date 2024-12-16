package com.csed.Mail.commands;

import com.csed.Mail.model.Dtos.MoveDto;
import com.csed.Mail.model.MailEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Command {
    protected MailEntity mailEntity;
    protected MoveDto moveDto;
    abstract void execute();
}
