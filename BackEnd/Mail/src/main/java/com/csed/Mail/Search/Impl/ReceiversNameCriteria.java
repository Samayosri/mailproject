package com.csed.Mail.Search.Impl;

import com.csed.Mail.Search.Criteria;
import com.csed.Mail.model.Dtos.MailDto;
import com.csed.Mail.model.UserEntity;
import com.csed.Mail.repositories.UserRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class ReceiversNameCriteria extends Criteria {
    @Autowired
    private UserRepository userRepository;

    public ReceiversNameCriteria(@NonNull String keyword) {
        super(keyword.trim());
    }


    @Override
    public List<MailDto> meetCriteria(List<MailDto> mailDtos) {
        List<MailDto> result = new ArrayList<>();

        for (MailDto mailDto : mailDtos) {
            if (containsKeyword(getReceiversName(mailDto.getToReceivers())) ||
                    containsKeyword(getReceiversName(mailDto.getCcReceivers())) ||
                    containsKeyword(getReceiversName(mailDto.getBccReceivers()))) {
                result.add(mailDto);
            }
        }

        return result;
    }

    private boolean containsKeyword(List<String> receivers) {
        if (receivers == null) {
            return false;
        }
        for (String name : receivers) {
            if (name != null && name.toLowerCase().contains(super.keyword.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    private List<String> getReceiversName(List<String> receiversEmailAddresses) {
        List<String> receiversNames = new ArrayList<>();
        if (receiversEmailAddresses == null || receiversEmailAddresses.isEmpty()) {
            return receiversNames;
        }

        for (String emailAddress : receiversEmailAddresses) {
            Optional<UserEntity> receiver = userRepository.findByEmailAddress(emailAddress);
            if (receiver.isPresent() && receiver.get().getName() != null) {
                receiversNames.add(receiver.get().getName());
            }
        }

        return receiversNames;
    }
}
