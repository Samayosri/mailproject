package com.csed.Mail.Services.Impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;

public class WebSocktingService {
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public WebSocktingService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendMessage(String message) {
        messagingTemplate.convertAndSend("/send/inbox", message);
    }
}