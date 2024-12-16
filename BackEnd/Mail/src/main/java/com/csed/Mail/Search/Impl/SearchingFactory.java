package com.csed.Mail.Search.Impl;

import com.csed.Mail.Search.Criteria;


public class SearchingFactory {
    public Criteria SearchBy(String seachCriteria,String Keyword){
        return switch (seachCriteria) {
            case "sender email address" -> new SenderEmailAddressCriteria(Keyword);
            case "sender name " -> new SenderNameCriteria(Keyword);
            case "subject " -> new SubjectCriteria(Keyword);
            case "receivers name" -> new ReceiversNameCriteria(Keyword);
            case "receivers email address" -> new ReceiversEmailAddressCriteria(Keyword);
            case "date" -> new DateCriteria(Keyword);
            case "importance" -> new ImportanceCriteria(Keyword);
            default -> null;
        };

    }
}
