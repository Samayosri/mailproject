package com.csed.Mail.Services.SearchingService.Impl;

import com.csed.Mail.Services.SearchingService.ISearchCriteria;
import com.csed.Mail.model.MailEntity;

import java.util.List;

public class SearchBySender implements ISearchCriteria {
    @Override
    public List<MailEntity> search() {

        return List.of();
    }
}
