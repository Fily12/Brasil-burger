package com.brasilburger.service;

import com.brasilburger.model.Commande;
import com.brasilburger.model.Client;
import com.brasilburger.model.Burger;
import com.brasilburger.model.Menu;
import com.brasilburger.model.Complement;
import com.brasilburger.model.LigneCommande;
import com.brasilburger.model.enums.StatutCommande;
import com.brasilburger.model.enums.TypeCommande;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CommandeService {
    private final List<Commande> commandes = new ArrayList<>();
    private long nextId = 1;

    public Commande creerCommande(Client client, TypeCommande type) {
        Commande c = new Commande(nextId++, client, type);
        commandes.add(c);
        return c;
    }

    public void ajouterBurger(Commande commande, Burger burger, int qte) {
        commande.ajouterLigne(new LigneCommande(burger, qte));
    }

    public void ajouterMenu(Commande commande, Menu menu, int qte) {
        commande.ajouterLigne(new LigneCommande(menu, qte));
    }

    public void ajouterComplement(Commande commande, Complement complement) {
        commande.ajouterLigne(new LigneCommande(complement));
    }

    public boolean payerCommande(Long id) {
        Optional<Commande> opt = trouverParId(id);
        if (opt.isPresent()) {
            opt.get().setStatut(StatutCommande.PAYEE);
            return true;
        }
        return false;
    }

    public boolean annulerCommande(Long id) {
        Optional<Commande> opt = trouverParId(id);
        if (opt.isPresent()) {
            com.brasilburger.model.User current = com.brasilburger.security.SecurityContext.getCurrentUser();
            if (current == null) return false;
            // Gestionnaire peut annuler n'importe quelle commande
            if (current.getRole() == com.brasilburger.model.enums.Role.GESTIONNAIRE) {
                opt.get().setStatut(StatutCommande.ANNULEE);
                return true;
            }
            // Client ne peut annuler que ses propres commandes
            if (current.getRole() == com.brasilburger.model.enums.Role.CLIENT) {
                if (opt.get().getClient() != null && opt.get().getClient().getUserId().equals(current.getId())) {
                    opt.get().setStatut(StatutCommande.ANNULEE);
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    public Optional<Commande> trouverParId(Long id) {
        return commandes.stream().filter(c -> c.getId().equals(id)).findFirst();
    }

    public List<Commande> listerCommandesClient(Long clientId) {
        List<Commande> out = new ArrayList<>();
        for (Commande c : commandes) if (c.getClient() != null && c.getClient().getId().equals(clientId)) out.add(c);
        return out;
    }

    public List<Commande> listerToutesCommandes() { return new ArrayList<>(commandes); }

    public List<Commande> listerCommandesParClient(String nom, String prenom, String telephone) {
        List<Commande> out = new ArrayList<>();
        for (Commande c : commandes) {
            if (c.getClient() == null) continue;
            boolean match = (nom == null || nom.isEmpty() || c.getClient().getNom().equalsIgnoreCase(nom))
                         && (prenom == null || prenom.isEmpty() || c.getClient().getPrenom().equalsIgnoreCase(prenom))
                         && (telephone == null || telephone.isEmpty() || c.getClient().getTelephone().equalsIgnoreCase(telephone));
            if (match) out.add(c);
        }
        return out;
    }
}
