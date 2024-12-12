package com.csed.Mail.model.Dtos;


import com.csed.Mail.model.FolderEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashSet;
import java.util.Set;

public class UserDto {
    private Long id;
    private String name;
    private String emailAddress;
    private String password;
    private Set<FolderEntity> folders = new HashSet<>();
    @JsonIgnore
    public Long getId() {
        return id;
    }

    public Set<FolderEntity> getFolders() {
        return folders;
    }

    public void setFolders(Set<FolderEntity> folders) {
        this.folders = folders;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
