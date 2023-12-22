package fr.insacvl.asl.bcn.bookzone.dtos;
import fr.insacvl.asl.bcn.bookzone.entities.CategorieEnum;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RechercheDTO {
    private String contenu;
    private List<CategorieEnum> categories;
}
