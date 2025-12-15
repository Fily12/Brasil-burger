package com.brasilburger.model.enums;

public enum TypeCommande {
    SUR_PLACE("Sur place"),
    A_EMPORTER("À emporter"),
    LIVRAISON("Livraison");

    private final String libelle;
    TypeCommande(String libelle) { this.libelle = libelle; }
    public String getLibelle() { return libelle; }
}
