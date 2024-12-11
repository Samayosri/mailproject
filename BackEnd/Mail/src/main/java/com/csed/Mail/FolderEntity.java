package com.csed.Mail;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class FolderEntity {

    @Id

    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)

    private UserEntity ownerId;

    private String name;

    @ManyToMany(cascade = CascadeType.ALL)

    private Set<MailEntity> emails = new HashSet<>();
}
