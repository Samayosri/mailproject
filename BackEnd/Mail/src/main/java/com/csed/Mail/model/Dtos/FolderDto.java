package com.csed.Mail.model.Dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FolderDto {
    private Long id; //used to fetch emails
    private Long userId;
    private String name;
}
