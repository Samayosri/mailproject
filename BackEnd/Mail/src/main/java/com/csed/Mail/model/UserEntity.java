package com.csed.Mail.model;

import com.csed.Mail.model.Dtos.UserDto;
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
@Table(name = "Users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String emailAddress;

    @Column(nullable = false)
    private String password;
    @OneToMany(mappedBy = "sender",fetch = FetchType.EAGER)
    private List<MailEntity> mails = new ArrayList<>();

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.EAGER,orphanRemoval = true)
    private List<FolderEntity> folders = new ArrayList<>();
    @JsonIgnore
    public UserDto getuserdto(){
        return UserDto.builder().id(id).name(name).emailAddress(emailAddress).build();
    }
}
