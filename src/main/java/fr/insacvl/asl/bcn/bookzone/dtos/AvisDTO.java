package fr.insacvl.asl.bcn.bookzone.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AvisDTO {

    @Min(value = 0, message = "The value must be positive")
    @Max(value = 5, message = "The value can't be higher than 5")
    private int note;

    private String commentaire;
}
