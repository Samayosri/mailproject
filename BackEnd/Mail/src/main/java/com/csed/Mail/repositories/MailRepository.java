package com.csed.Mail.repositories;
import com.csed.Mail.model.MailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface MailRepository extends JpaRepository<MailEntity, Long> {
}

