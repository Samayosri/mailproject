package com.csed.Mail.Search;

import com.csed.Mail.Search.Impl.*;
import com.csed.Mail.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SearchingFactory {
    private final UserRepository userRepository;

    @Autowired
    public SearchingFactory(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public Criteria SearchBy(String Keyword, String searchCriteria){
        return switch (searchCriteria) {
            case "senderEmailAddress" -> new SenderEmailAddressCriteria(Keyword);
            case "senderName" -> new SenderNameCriteria(Keyword, userRepository);
            case "subject" -> new SubjectCriteria(Keyword);
            case "body" -> new BodyCriteria(Keyword);
            case "receiversName" -> new ReceiversNameCriteria(Keyword, userRepository);
            case "receiversEmailAddress" -> new ReceiversEmailAddressCriteria(Keyword);
            case "date" -> new DateCriteria(Keyword);
            case "attachments" -> new AttachmentsCriteria(Keyword);
            case "importance" -> new ImportanceCriteria(Keyword);
            default -> null;
        };
    }
}
