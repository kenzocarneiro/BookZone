package fr.insacvl.asl.bcn.bookzone.dtos;

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
public class OuvrageDTO {

    @NotEmpty
    private String titre;
    @NotEmpty
    private String editeur;
    @Min(value = 0, message = "The value must be positive")
    private int nbPages;
}
