package com.csed.Mail.repositories;

import com.csed.Mail.model.DeletedMailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeletedMailsRepository extends JpaRepository<DeletedMailEntity, Long> {
    void deleteByMail(Long mailId);
    Optional<DeletedMailEntity> findByMail(Long mailId);

}