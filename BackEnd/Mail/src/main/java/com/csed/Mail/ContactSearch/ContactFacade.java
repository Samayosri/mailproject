package com.csed.Mail.ContactSearch;

import com.csed.Mail.ContactSearch.Dtos.ContactCriteriaDto;
import com.csed.Mail.ContactSearch.Impl.ContactOrCriteria;
import com.csed.Mail.Services.ContactServices;
import com.csed.Mail.Sorting.ISortContact;
import com.csed.Mail.model.Dtos.ContactDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContactFacade {

    private final ContactServices contactService;
    private final ISortContact sortContact;
    private final ContactCriteriaFactory contactCriteriaFactory;
    public ContactFacade(ContactServices contactService, ISortContact sortContact, ContactCriteriaFactory contactCriteriaFactory) {
        this.contactService = contactService;
        this.sortContact = sortContact;
        this.contactCriteriaFactory = contactCriteriaFactory;
    }

    private List<ContactCriteria> getListOfCriterias(ContactCriteriaDto criteriaDto) {
        List<ContactCriteria> result = new ArrayList<>();
        if (criteriaDto.getCriterias() != null) {
            for (String criteria : criteriaDto.getCriterias()) {
                if(criteria != null) {
                    ContactCriteria criteriaFilter = this.contactCriteriaFactory.SearchBy(criteriaDto.getSearchWord(), criteria);
                    if(criteriaFilter != null) {
                        result.add(criteriaFilter);
                    }
                }
            }
        }
        return result;
    }
    public List<ContactDto> getContacts(Long userId, ContactCriteriaDto criteriaDto) {
        List<ContactDto> result = contactService.getAllContacts(userId);
        List<ContactCriteria> criterias = getListOfCriterias(criteriaDto);
        ContactCriteria orCriteria = new ContactOrCriteria(criteriaDto.getSearchWord(), criterias);
        result = orCriteria.meetCriteria(result);
        result = sortContact.SortingContact(result);

        return result;
    }
}
