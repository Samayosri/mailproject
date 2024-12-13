package com.csed.Mail.model;

import com.csed.Mail.model.Dtos.FolderDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

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
@Table(name = "Folders")
public class FolderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    @ManyToOne
    @JoinColumn(nullable = false)
    private UserEntity owner;

    @Column(nullable = false)
    private String name;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "folder_emails",
            joinColumns = @JoinColumn(name = "folder_id"),
            inverseJoinColumns = @JoinColumn(name = "mail_id")
    )
    private List<MailEntity> emails = new ArrayList<>();
    @JsonIgnore
    public FolderDto getDto(){
        return FolderDto.builder().id(id).userId(owner.getId()).name(name).build();
    }
}

