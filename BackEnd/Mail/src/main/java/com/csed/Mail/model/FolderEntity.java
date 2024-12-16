package com.csed.Mail.model;

import com.csed.Mail.model.Dtos.FolderDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Folders")
public class FolderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    @ManyToOne
    private UserEntity owner;

    @Column(nullable = false)
    private String name;

    @ManyToMany(cascade =CascadeType.ALL ,fetch = FetchType.EAGER)
    private List<MailEntity> emails = new ArrayList<>();
    @JsonIgnore
    public FolderDto getDto(){
        return FolderDto.builder().id(id).userId(owner.getId()).name(name).build();
    }
}

