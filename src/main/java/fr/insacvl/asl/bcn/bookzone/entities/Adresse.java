//package fr.insacvl.asl.bcn.bookzone.entities;
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.Setter;
//
//
//@AllArgsConstructor
//@Getter
//@Entity
//public class Adresse {
//
//    @Id @GeneratedValue
//    private int idAdresse;
//
//    @Setter private String rue;
//    @Setter private String ville;
//    @Setter private int codePostal;
//    @Setter private String pays;
//    @Setter private String informationsComplementaires;
//
//    @OneToOne
//    @Setter private Utilisateur utilisateur;
//
//    public Adresse() {
//
//    }
//}