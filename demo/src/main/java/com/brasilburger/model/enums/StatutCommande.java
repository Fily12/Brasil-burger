package com.brasilburger.model.enums;

public enum StatutCommande {
    EN_ATTENTE("En attente"),
    PAYEE("Payée"),
    ANNULEE("Annulée");

    private final String libelle;
    StatutCommande(String libelle) { this.libelle = libelle; }
    public String getLibelle() { return libelle; }
}
