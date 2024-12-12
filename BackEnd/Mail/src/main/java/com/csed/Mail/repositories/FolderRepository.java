package com.csed.Mail.repositories;

import com.csed.Mail.model.FolderEntity;
import com.csed.Mail.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FolderRepository extends JpaRepository<FolderEntity, Long> {

    Optional<FolderEntity> getFolderByName(String name);
    Optional<FolderEntity> getFolderByOwnerId(Long ownerId);
    Optional<FolderEntity> findByOwnerAndName(UserEntity owner ,String name);

}
