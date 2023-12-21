package fr.insacvl.asl.bcn.bookzone.dtos;

import fr.insacvl.asl.bcn.bookzone.entities.Personne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OuvrageDTO {

    @NotEmpty
    private String titre;
    @NotEmpty
    private String editeur;
    @Min(value = 0, message = "The value must be positive")
    private int nbPages;
    @NotEmpty
    private Set<Personne> auteurs = new HashSet<>();
}
