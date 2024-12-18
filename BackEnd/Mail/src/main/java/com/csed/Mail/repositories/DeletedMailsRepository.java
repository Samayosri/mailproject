package com.csed.Mail.repositories;

import com.csed.Mail.model.DeletedMailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeletedMailsRepository extends JpaRepository<DeletedMailEntity, Long> {
    void deleteByMailId(Long mailId);
    List<DeletedMailEntity> findAllByFolderId(Long folderId);
    void deleteAllByMailIdIn(List<Long> mailIdsToDelete);
}