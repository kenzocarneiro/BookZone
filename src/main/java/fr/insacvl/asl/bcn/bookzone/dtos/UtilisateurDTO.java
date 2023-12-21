package fr.insacvl.asl.bcn.bookzone.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UtilisateurDTO {
    @NotEmpty
    private String prenom;
    @NotEmpty
    private String nom;
    @NotEmpty
    private String mail;
    @NotEmpty
    private String login;
    @NotEmpty
    private String password;
    private String role;
    @NotEmpty
    private String adresse;
    private boolean libraire;
}
