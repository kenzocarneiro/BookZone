package fr.insacvl.asl.bcn.bookzone.entities;
import jakarta.persistence.*;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Utilisateur extends Personne {

    @Column(unique = true)
    @NonNull @Setter private String mail;

    @Column(unique = true)
    @NonNull @Setter private String login;

    @NonNull @Setter private String password;
    @NonNull @Setter private String role;

    @OneToOne
    @JoinColumn(name="id_adresse")
    @Setter private Adresse adresse;

}