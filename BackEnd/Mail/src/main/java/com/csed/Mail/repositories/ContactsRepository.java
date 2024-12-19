package com.csed.Mail.repositories;

import com.csed.Mail.model.ContactEntity;
import com.csed.Mail.model.Dtos.ContactDto;
import com.csed.Mail.model.FolderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContactsRepository extends JpaRepository<ContactEntity,Long>{
     Optional<ContactEntity> findByName(String name);
    List<ContactEntity> findByOwnerId(Long ownerid);
    Optional<ContactEntity> findByNameAndOwnerId(String name, Long userId);

}
