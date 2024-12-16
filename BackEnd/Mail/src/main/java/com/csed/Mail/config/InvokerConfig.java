package com.csed.Mail.config;

import com.csed.Mail.commands.CommandService;
import com.csed.Mail.commands.Invoker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.PutMapping;

//@Configuration
//public class InvokerConfig {
//    private final CommandService commandService;
//
//    public InvokerConfig(CommandService commandService) {
//        this.commandService = commandService;
//    }
//    @Bean
//    public Invoker getInvoker(){return new Invoker(commandService);}
//}
