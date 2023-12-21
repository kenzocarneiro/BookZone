package fr.insacvl.asl.bcn.bookzone.entities;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Utilisateur extends Personne {

    @Column(unique = true)
    @NotNull private String mail;

    @Column(unique = true)
    @NotNull private String login;

    @NotNull private String password;
    @NotNull private String role;

    @OneToOne
    @JoinColumn(name="id_adresse")
    private Adresse adresse;
}