package com.brasilburger.model;

import com.brasilburger.model.enums.StatutCommande;
import com.brasilburger.model.enums.TypeCommande;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Commande {
    private Long id;
    private Client client;
    private LocalDateTime dateCommande = LocalDateTime.now();
    private TypeCommande typeCommande;
    private StatutCommande statut = StatutCommande.EN_ATTENTE;
    private final List<LigneCommande> lignes = new ArrayList<>();

    public Commande() {}

    public Commande(Long id, Client client, TypeCommande typeCommande) {
        this.id = id;
        this.client = client;
        this.typeCommande = typeCommande;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client; }
    public LocalDateTime getDateCommande() { return dateCommande; }
    public TypeCommande getTypeCommande() { return typeCommande; }
    public void setTypeCommande(TypeCommande typeCommande) { this.typeCommande = typeCommande; }
    public StatutCommande getStatut() { return statut; }
    public void setStatut(StatutCommande statut) { this.statut = statut; }
    public List<LigneCommande> getLignes() { return lignes; }

    public void ajouterLigne(LigneCommande ligne) { lignes.add(ligne); }

    public double getMontantTotal() {
        return lignes.stream().mapToDouble(LigneCommande::getMontant).sum();
    }
}
