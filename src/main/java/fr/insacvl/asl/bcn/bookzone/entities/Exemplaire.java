//package fr.insacvl.asl.bcn.bookzone.entities;
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NonNull;
//import lombok.Setter;
//
//
//@AllArgsConstructor
//@Getter
//@Entity
//public class Exemplaire {
//
//    @Id @GeneratedValue
//    private int idExemplaire;
//
//    @NonNull @Setter private String etat;
//    @Setter private float prixVente;
//    @Setter private float fraisPort;
//
//    @ManyToOne
//    @Setter private Libraire vendeur;
//
//    @OneToOne(mappedBy = "idAvis")
//    @Setter Avis avis;
//
//    @ManyToOne
//    @Setter private Commande commande;
//    public Exemplaire() {
//    }
//}