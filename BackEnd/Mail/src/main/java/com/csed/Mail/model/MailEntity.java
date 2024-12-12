package com.csed.Mail.model;

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

//    private Integer threadId;

    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "sender_id", nullable = false)
    private UserEntity sender;

    @Column(nullable = false)
    private LocalDateTime creationDate;

    @Column(nullable = false)
    private String subject;

    @Lob
    private String body;

    @ElementCollection
    private Set<String> toReceivers = new HashSet<>();

    @ElementCollection
    private Set<String> ccReceivers = new HashSet<>();

    @ElementCollection
    private Set<String> bccReceivers = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private Integer importance;

    @OneToMany(mappedBy = "mail", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<AttachmentEntity> attachments = new HashSet<>();

    @ManyToMany(mappedBy = "emails", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private Set<FolderEntity> folders = new HashSet<>();

    @PrePersist
    protected void onCreate() {
        if (this.creationDate == null) {
            this.creationDate = LocalDateTime.now();
        }
        if (this.importance == null) {
            this.importance = 3;
        }
        // low 4
        // medium 3
        // high 2
        // urgent 1
    }
}