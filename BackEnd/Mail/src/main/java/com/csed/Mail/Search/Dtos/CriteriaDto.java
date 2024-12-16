package com.csed.Mail.Search.Dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CriteriaDto {

    private String searchWord;

    private List<String> criterias;
    // {
    //      senderEmailAddress, senderName, subject, body, receiversName,
    //      receiversEmailAddress, date, attachments, importance
    // }

    @Builder.Default
    private String sortedBy = "date"; // {date, importance}

    @Builder.Default
    private int pageNumber = 0;

    @Builder.Default
    private int pageSize = 5;
}
