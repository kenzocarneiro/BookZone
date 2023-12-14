package fr.insacvl.asl.bcn.bookzone.entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@AllArgsConstructor
@Getter
@Entity
public class Utilisateur extends Personne {

    @Column(unique = true)
    @Setter private String mail;
    @Column(unique = true)
    @Setter private String login;
    @Setter private String password;

//    @OneToOne(mappedBy = "idAdresse")
//    @Setter private Adresse adresse;

    public Utilisateur() {

    }
}