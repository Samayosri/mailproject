package com.csed.Mail.repositories;

import com.csed.Mail.model.MailEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;



@Repository
public interface MailRepository extends JpaRepository<MailEntity, Long> {

    // Query to find emails in a given folder for a given user with attachments
    @Query("SELECT m FROM MailEntity m " +
            "JOIN m.folders f " +
            "JOIN FETCH m.attachments a " +  // Using FETCH to eagerly load attachments
            "WHERE f.id = :folderId AND f.owner.id = :userId " +
            "ORDER BY m.creationDate DESC")
    Page<MailEntity> findEmailsInFolderWithAttachments(
            @Param("userId") Long userId,
            @Param("folderId") Long folderId,
            Pageable pageable
    );


}

