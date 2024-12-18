package com.csed.Mail.repositories;

import com.csed.Mail.model.DeletedMailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface DeletedMailsRepository extends JpaRepository<DeletedMailEntity, Long> {

    @Modifying
    @Transactional
    @Query("DELETE FROM DeletedMailEntity d WHERE d.mail = :mailId")
    void deleteByMail(@Param("mailId") Long mailId);
    Optional<DeletedMailEntity> findByMail(Long mailId);

}