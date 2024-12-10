package entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Mail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "email_id_sequencer")
    private Long id;
    private Integer threadId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name= "sender_id")
    private User sender;

    @Column(nullable = false)
    private LocalDateTime creationDate;

    private String subject;
    private String body;

    private List<String> toReceivers;
    private List<String> ccReceivers;

    @Enumerated(EnumType.STRING)
    private Importance importance;

    @OneToMany(mappedBy = "mail", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Attachment> attachments = new ArrayList<>();


    @ManyToMany(mappedBy = "emails", cascade = CascadeType.ALL)
    private List<Folder> folders = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        this.creationDate = LocalDateTime.now();
        if (this.importance == null)
            this.importance = Importance.NORMAL;
    }


}
