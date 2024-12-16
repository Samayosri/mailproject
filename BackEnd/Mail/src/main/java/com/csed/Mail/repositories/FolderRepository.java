package com.csed.Mail.repositories;

import com.csed.Mail.model.FolderEntity;
import com.csed.Mail.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FolderRepository extends JpaRepository<FolderEntity, Long> {
     List<FolderEntity> findByOwnerId(Long userId);
    Optional<FolderEntity> findByOwnerAndName(UserEntity owner ,String name);
    Optional<FolderEntity> getFolderByName(String name);
}
