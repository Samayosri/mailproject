package com.csed.Mail.model;

import com.csed.Mail.enums.Importance;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Mails")
public class MailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer threadId;

    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "sender_id", nullable = false)
    private UserEntity sender;

    @Column(nullable = false)
    private LocalDateTime creationDate;

    @Column(nullable = false)
    private String subject;

    @Lob
    private String body;

    @ManyToMany
    @JoinTable(
            name = "mail_to_receivers",
            joinColumns = @JoinColumn(name = "mail_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<UserEntity> toReceivers = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "mail_cc_receivers",
            joinColumns = @JoinColumn(name = "mail_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<UserEntity> ccReceivers = new HashSet<>();

    @JoinTable(
            name = "mail_bcc_receivers",
            joinColumns = @JoinColumn(name = "mail_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<UserEntity> bccReceivers = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private Importance importance;

    @OneToMany(mappedBy = "mail", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<AttachmentEntity> attachments = new HashSet<>();

    @ManyToMany(mappedBy = "emails", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private Set<FolderEntity> folders = new HashSet<>();

    @PrePersist
    protected void onCreate() {
        this.creationDate = LocalDateTime.now();
        if (this.importance == null)
            this.importance = Importance.NORMAL;
    }
}