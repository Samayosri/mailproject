package com.csed.Mail.model.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MoveDto {
    private Long userId;
    private Long sourceFolderId;
    private Long destinationFolderId;
    private List<Long> mailIds;
}
