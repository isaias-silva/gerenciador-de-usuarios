package com.zack.api.repositories;

import com.zack.api.models.EmailModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.UUID;

public interface EmailRepository extends JpaRepository<EmailModel, UUID> {

    @Query("SELECT e.id , e.toMail,e.fromMail,e.subject FROM EmailModel e")
    Page<EmailModel> getResumeEmails(Pageable pageable);
}
