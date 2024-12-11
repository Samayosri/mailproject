package com.csed.Mail;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class AttachmentEntity implements Serializable {

    @Id
    private Long id;

    @ManyToOne

    private MailEntity mail;

    private String fileName;

    private String fileType;


    @Lob
    private byte[] file;
}
