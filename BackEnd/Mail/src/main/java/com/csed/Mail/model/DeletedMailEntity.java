package com.csed.Mail.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class DeletedMailEntity {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    private Long mail;

    @Column(nullable = false)
    private LocalDateTime deletionTime;

    @PrePersist
    protected void onDelete() {
        if (this.deletionTime == null) {
            this.deletionTime = LocalDateTime.now();
        }
    }
}
