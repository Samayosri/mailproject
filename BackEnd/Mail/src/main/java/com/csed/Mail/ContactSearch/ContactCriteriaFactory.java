package com.csed.Mail.ContactSearch;

import com.csed.Mail.ContactSearch.Impl.ContactEmailCriteria;
import com.csed.Mail.ContactSearch.Impl.ContactNameCriteria;
import org.springframework.stereotype.Component;

@Component
public class ContactCriteriaFactory {
    public ContactCriteria SearchBy(String Keyword, String searchCriteria){
        return switch (searchCriteria) {
            case "name" -> new ContactNameCriteria(Keyword);
            case "emailAddress" -> new ContactEmailCriteria(Keyword);
            default -> null;
        };
    }
}
