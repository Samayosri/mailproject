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
public class MailEntity {

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

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> toReceivers = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> ccReceivers = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> bccReceivers = new ArrayList<>();

    private Integer importance;

    @OneToMany(mappedBy = "mail", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<AttachmentEntity> attachments = new ArrayList<>();

    @ManyToMany(mappedBy = "emails", fetch = FetchType.EAGER)
    private List<FolderEntity> folders = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        if (this.creationDate == null) {
            this.creationDate = LocalDateTime.now();
        }
        if (this.importance == null) {
            this.importance = 3;
        }
    }
}