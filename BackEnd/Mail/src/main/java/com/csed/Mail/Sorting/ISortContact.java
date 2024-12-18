package com.csed.Mail.Sorting;

import com.csed.Mail.model.ContactEntity;
import com.csed.Mail.model.Dtos.ContactDto;

import java.util.List;

public interface ISortContact {
    List<ContactDto> SortingContact( List<ContactDto> contactDtos);
}
