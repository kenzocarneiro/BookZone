package fr.insacvl.asl.bcn.bookzone.entities;
import jakarta.persistence.*;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Utilisateur extends Personne {

    @Column(unique = true)
    @NonNull private String mail;

    @Column(unique = true)
    @NonNull private String login;

    @NonNull private String password;
    @NonNull private String role;

    @OneToOne
    @JoinColumn(name="id_adresse")
    private Adresse adresse;
}