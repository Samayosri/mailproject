package com.csed.Mail.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Transactional
@Table(name = "Mails")
public class MailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sender_id", nullable = false)
    private UserEntity sender;

    @Column(nullable = false)
    private LocalDateTime creationDate;

    @Column(nullable = false)
    private String subject;

    private String body;

    @ElementCollection
    private List<String> toReceivers = new ArrayList<>();
    @ElementCollection
    private List<String> ccReceivers = new ArrayList<>();
    @ElementCollection
    private List<String> bccReceivers = new ArrayList<>();

    private Integer importance;

    @OneToMany(mappedBy = "mail", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<AttachmentEntity> attachments = new ArrayList<>();

    @ManyToMany(mappedBy = "emails", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<FolderEntity> folders =new ArrayList<>();

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