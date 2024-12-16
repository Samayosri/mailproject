package com.csed.Mail.Search.Impl;

import com.csed.Mail.Search.Criteria;
import com.csed.Mail.model.Dtos.MailDto;
import com.csed.Mail.model.UserEntity;
import com.csed.Mail.repositories.UserRepository;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;


public class SenderNameCriteria extends Criteria {

    private final UserRepository userRepository;

    @Autowired
    public SenderNameCriteria(@NonNull String keyword, UserRepository userRepository) {
        super(keyword.trim());
        this.userRepository = userRepository;
    }

    @Override
    public List<MailDto> meetCriteria(List<MailDto> mailDtos) {
        Set<Long> senderIds = new HashSet<>();
        for (MailDto mailDto : mailDtos) {
            if (mailDto.getSenderId() != null) {
                senderIds.add(mailDto.getSenderId());
            }
        }

        Map<Long, String> senderNames = new HashMap<>();
        Iterable<UserEntity> users = userRepository.findAllById(senderIds);
        for (UserEntity user : users) {
            if (user.getName() != null) {
                senderNames.put(user.getId(), user.getName().toLowerCase());
            }
        }

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
