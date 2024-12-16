package com.csed.Mail.Search;
import com.csed.Mail.Pagination.Pager;
import com.csed.Mail.Search.Dtos.CriteriaDto;
import com.csed.Mail.Search.Impl.OrCriteria;
import com.csed.Mail.Services.MailService;
import com.csed.Mail.Sorting.SortingStrategy;
import com.csed.Mail.mappers.Mapper;
import com.csed.Mail.model.Dtos.MailDto;
import com.csed.Mail.model.FolderEntity;
import com.csed.Mail.model.MailEntity;
import com.csed.Mail.model.UserEntity;
import com.csed.Mail.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CriteriaFacade {

    private final MailService mailService;
    private final UserRepository userRepository;
    private final SearchingFactory searchingFactory;
    private final Map<String, SortingStrategy> sortingStrategies;
    private final Mapper<MailEntity, MailDto> mailMapper;
    private final Pager pager;

    @Autowired
    public CriteriaFacade(MailService mailService,UserRepository userRepository, SearchingFactory searchingFactory, Map<String, SortingStrategy> sortingStrategies, Mapper<MailEntity, MailDto> mailMapper, Pager pager) {
        this.mailService = mailService;
        this.userRepository = userRepository;
        this.searchingFactory = searchingFactory;
        this.sortingStrategies = sortingStrategies;
        this.mailMapper = mailMapper;
        this.pager = pager;
    }

    private List<MailDto> getMailsFromDateBase(Long userId, Long folderId) {
        if(folderId == null) { //get all mails for the user
            Optional<UserEntity> user = userRepository.findById(userId);
            if (user.isPresent()) {
                List<FolderEntity> folders = user.get().getFolders();
                Set<MailDto> mailDtosSet = new HashSet<>(); // Use a Set to avoid duplicates

                for (FolderEntity folderEntity : folders) {
                    List<MailDto> mailsFromFolder = mailService.getListEmailsByFolderId(folderEntity.getId());
                    mailDtosSet.addAll(mailsFromFolder); // Add mails from each folder
                }

                // Convert the Set to a List (if you need a List as the return type)
                List<MailDto> mailDtos = new ArrayList<>(mailDtosSet);
                System.out.println(mailDtos);
                return mailDtos;
            } else {
                throw new IllegalArgumentException("No such user with id " + userId);
            }
        }
        else { // get the mails that are in a specific folder
            return mailService.getListEmailsByFolderId(folderId);
        }
    }

    private List<Criteria> getListOfCriterias(CriteriaDto criteriaDto) {
        List<Criteria> result = new ArrayList<>();
        if (criteriaDto.getCriterias() != null) {
            for (String criteria : criteriaDto.getCriterias()) {
                if(criteria != null) {
                    Criteria criteriaFilter = this.searchingFactory.SearchBy(criteriaDto.getSearchWord(), criteria);
                    if(criteriaFilter != null) {
                        result.add(criteriaFilter);
                    }
                }
            }
        }
        return result;
    }

    public List<MailDto> getMails(Long userId, Long folderId, CriteriaDto criteriaDto) {
        List<MailDto> mailDtos = getMailsFromDateBase(userId, folderId);


        List<Criteria> criterias = getListOfCriterias(criteriaDto);
        Criteria orCriteria = new OrCriteria(criteriaDto.getSearchWord(), criterias);
        mailDtos = orCriteria.meetCriteria(mailDtos);


        SortingStrategy sortingStrategy = sortingStrategies.get(criteriaDto.getSortedBy());
        if (sortingStrategy != null) {
            mailDtos = sortingStrategy.sort(mailDtos);
        }

        mailDtos = pager.page(mailDtos, criteriaDto.getPageNumber(), criteriaDto.getPageSize());

        return mailDtos;
    }
}