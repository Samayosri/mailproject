package com.csed.Mail.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Mails")
public class MailEntity implements Cloneable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private UserEntity sender;

    @Column(nullable = false)
    private LocalDateTime creationDate;

    @Column(nullable = false)
    private String subject;

    private String body;
    private String state;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> toReceivers = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> ccReceivers = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> bccReceivers = new ArrayList<>();

    private Integer importance;

    @OneToMany(mappedBy = "mail",cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
    private List<AttachmentEntity> attachments = new ArrayList<>();

    @ManyToOne
    private FolderEntity folder;

    @PrePersist
    protected void onCreate() {
        if (this.creationDate == null) {
            this.creationDate = LocalDateTime.now();
        }
        if (this.importance == null) {
            this.importance = 3;
        }
    }

    @Override
    public MailEntity clone() {
        try {
            MailEntity clone = (MailEntity) super.clone();
            clone.setId(null);
            clone.setToReceivers(new ArrayList<>(toReceivers));
            clone.setCcReceivers(new ArrayList<>(ccReceivers));
            clone.setAttachments(new ArrayList<>());
            for(AttachmentEntity a : attachments){
               AttachmentEntity atclone = a.clone();
                atclone.setMail(clone);
                clone.attachments.add(atclone);
            }

            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}