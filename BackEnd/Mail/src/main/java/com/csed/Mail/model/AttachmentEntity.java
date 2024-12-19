package com.csed.Mail.model;

import com.csed.Mail.model.Dtos.AttachmentDto;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Attachments")
public class AttachmentEntity implements Serializable,Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String fileType;

    @Column(name = "file_data")
    @Lob
    @Basic(fetch = FetchType.EAGER)
    private byte[] fileData;

    public AttachmentDto getDto(){
        return AttachmentDto.builder().file(fileData != null ? new String(fileData, StandardCharsets.UTF_8) : null).
                name(fileName).
                type(fileType).
                id(id).
                build();
    }

    @Override
    public AttachmentEntity clone() {
        try {
            AttachmentEntity clone = (AttachmentEntity) super.clone();
            clone.setId(null);
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }


}