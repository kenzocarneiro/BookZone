package fr.insacvl.asl.bcn.bookzone.dtos;

import fr.insacvl.asl.bcn.bookzone.entities.EtatExemplaire;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExemplaireDTO {

    private EtatExemplaire etat;
    @NotEmpty
    private String ouvrage;
    @Min(value = 0, message = "The value must be positive")
    private float prixVente;
    @Min(value = 0, message = "The value must be positive")
    private float fraisPort;
}
