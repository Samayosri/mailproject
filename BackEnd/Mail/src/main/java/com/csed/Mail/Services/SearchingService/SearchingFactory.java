package com.csed.Mail.Services.SearchingService;

import com.csed.Mail.Services.SearchingService.Impl.SearchBySender;

public class SearchingFactory {
    public ISearchCriteria SearchBy(String seachCriteria){
        switch (seachCriteria){
            case "sender":
                return new SearchBySender();

            default: return  null;
        }

    }
}
