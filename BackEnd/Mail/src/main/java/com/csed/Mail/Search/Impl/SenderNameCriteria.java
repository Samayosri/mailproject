package com.csed.Mail.Search.Impl;

import com.csed.Mail.Search.Criteria;
import com.csed.Mail.model.Dtos.MailDto;
import com.csed.Mail.model.UserEntity;
import com.csed.Mail.repositories.UserRepository;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;


public class SenderNameCriteria extends Criteria {

    @Autowired
    private UserRepository userRepository;

    public SenderNameCriteria(@NonNull String keyword) {
        super(keyword.trim());
    }


    @Override
    public List<MailDto> meetCriteria(List<MailDto> mailDtos) {
        Set<Long> senderIds = mailDtos.stream()
                .map(MailDto::getSenderId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        Map<Long, String> senderNames = userRepository.findAllById(senderIds).stream()
                .filter(user -> user.getName() != null)
                .collect(Collectors.toMap(UserEntity::getId, user -> user.getName().toLowerCase()));

        List<MailDto> result = new ArrayList<>();
        for (MailDto mailDto : mailDtos) {
            Long senderId = mailDto.getSenderId();
            if (senderId != null && senderNames.containsKey(senderId)) {
                String senderName = senderNames.get(senderId);
                if (senderName.contains(super.keyword.toLowerCase())) {
                    result.add(mailDto);
                }
            }
        }
        return result;
    }
}
