package com.csed.Mail.Services.SearchingService;

import com.csed.Mail.model.MailEntity;

import java.util.List;

public interface ISearchCriteria {
    List<MailEntity> search();
}
