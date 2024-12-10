package entities;

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
public class Folder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "email_id_sequencer")
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name= "sender_id")
    private User ownerId;

    private String name;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinColumn(name= "mail_id")
    private Set<Mail> emails = new HashSet<>();
}
