package com.brasilburger;

import com.brasilburger.model.*;
import com.brasilburger.model.enums.Role;
import com.brasilburger.model.enums.StatutCommande;
import com.brasilburger.model.enums.TypeCommande;
import com.brasilburger.service.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final BurgerService burgerService = new BurgerService();
    private static final ComplementService complementService = new ComplementService();
    private static final MenuService menuService = new MenuService();
    private static final UserService userService = new UserService();
    private static final ClientService clientService = new ClientService(userService);
    private static final CommandeService commandeService = new CommandeService();
    
    private static User utilisateurConnecte = null;
    private static Client clientConnecte = null;

    public static void main(String[] args) {
        initialiserDonnees();
        
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║   BIENVENUE CHEZ BRASIL BURGER  🍔     ║");
        System.out.println("╚════════════════════════════════════════╝");
        
        while (true) {
            afficherMenuAuthentification();
            int choix = lireEntier();
            
            switch (choix) {
                case 1 -> {
                    if (seConnecter()) {
                        com.brasilburger.security.SecurityContext.setCurrentUser(utilisateurConnecte);
                        if (utilisateurConnecte.getRole() == Role.GESTIONNAIRE) menuGestionnaire();
                        else menuClient();
                    }
                }
                case 2 -> creerCompteClient();
                case 3 -> {
                    System.out.println("\n👋 Merci et à bientôt chez Brasil Burger!");
                    return;
                }
                default -> System.out.println("❌ Choix invalide!");
            }
        }
    }

    private static void afficherMenuAuthentification() {
        System.out.println("\n─────────────────────────────────────");
        System.out.println("1. Se connecter");
        System.out.println("2. Créer un compte ");
        System.out.println("3. Quitter");
        System.out.print("➤ Votre choix: ");
    }

    private static boolean seConnecter() {
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Mot de passe: ");
        String mdp = scanner.nextLine();

        Optional<User> opt = userService.authentifier(email, mdp);
        if (opt.isPresent()) {
            utilisateurConnecte = opt.get();
            clientConnecte = clientService.trouverParUserId(utilisateurConnecte.getId()).orElse(null);
            System.out.println("✅ Connexion réussie!");
            return true;
        }
        System.out.println("❌ Identifiants invalides");
        return false;
    }

    private static void creerCompteClient() {
        System.out.println("\n📝 CRÉER UN COMPTE CLIENT");
        System.out.print("Nom: ");
        String nom = scanner.nextLine();
        System.out.print("Prénom: ");
        String prenom = scanner.nextLine();
        System.out.print("Téléphone: ");
        String telephone = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Mot de passe: ");
        String mdp = scanner.nextLine();

        clientService.creerCompte(nom, prenom, telephone, email, mdp);
        System.out.println("✅ Compte client créé avec succès!");
    }

    private static void menuGestionnaire() {
        while (true) {
            System.out.println("\n─────────────────────────────────────");
            System.out.println("GESTION DES MENUS");
            System.out.println("─────────────────────────────────────");
            System.out.println("1. Gestion des burgers");
            System.out.println("2. Gestion des menus");
            System.out.println("3. Gestion des compléments");
            System.out.println("4. Gestion des commandes");
            System.out.println("5. Retour");
            System.out.print("➤ Votre choix: ");

            int choix = lireEntier();

            switch (choix) {
                case 1 -> gestionBurgers();
                case 2 -> {
                    while (true) {
                        System.out.println("\n─────────────────────────────────────");
                        System.out.println("GESTION DES MENUS");
                        System.out.println("─────────────────────────────────────");
                        System.out.println("1. Ajouter un menu");
                        System.out.println("2. Modifier un menu");
                        System.out.println("3. Archiver un menu");
                        System.out.println("4. Lister les menus");
                        System.out.println("5. Retour");
                        System.out.print("➤ Votre choix: ");

                        int choixMenu = lireEntier();
                        switch (choixMenu) {
                            case 1 -> ajouterMenu();
                            case 2 -> modifierMenu();
                            case 3 -> archiverMenu();
                            case 4 -> listerMenus();
                            case 5 -> { break; }
                            default -> System.out.println("❌ Choix invalide!");
                        }
                    }
                }
                case 3 -> gestionComplements();
                case 4 -> gestionCommandes();
                case 5 -> { return; }
                default -> System.out.println("❌ Choix invalide!");
            }
        }
    }

    private static void gestionBurgers() {
        while (true) {
            System.out.println("\n─────────────────────────────────────");
            System.out.println("GESTION DES BURGERS");
            System.out.println("─────────────────────────────────────");
            System.out.println("1. Ajouter un burger");
            System.out.println("2. Modifier un burger");
            System.out.println("3. Archiver un burger");
            System.out.println("4. Lister les burgers");
            System.out.println("5. Retour");
            System.out.print("➤ Votre choix: ");

            int choix = lireEntier();
            switch (choix) {
                case 1 -> ajouterBurger();
                case 2 -> modifierBurger();
                case 3 -> archiverBurger();
                case 4 -> listerBurgers();
                case 5 -> { return; }
                default -> System.out.println("❌ Choix invalide!");
            }
        }
    }

    private static void ajouterBurger() {
        System.out.println("\n📝 AJOUTER UN BURGER");
        System.out.print("Nom: ");
        String nom = scanner.nextLine();
        System.out.print("Prix (FCFA): ");
        double prix = lireDouble();
        System.out.print("Image (URL): ");
        String image = scanner.nextLine();

        Burger b = burgerService.ajouter(nom, prix, image);
        System.out.println("✅ Burger ajouté avec succès! ID: " + b.getId());
    }

    private static void modifierBurger() {
        listerBurgers();
        System.out.print("\nID du burger à modifier: ");
        Long id = lireLong();

        Optional<Burger> opt = burgerService.trouverParId(id);
        if (opt.isPresent()) {
            System.out.print("Nouveau nom: ");
            String nom = scanner.nextLine();
            System.out.print("Nouveau prix: ");
            double prix = lireDouble();
            System.out.print("Nouvelle image: ");
            String image = scanner.nextLine();

            burgerService.modifier(id, nom, prix, image);
            System.out.println("✅ Burger modifié avec succès!");
        } else {
            System.out.println("❌ Burger introuvable!");
        }
    }

    private static void archiverBurger() {
        listerBurgers();
        System.out.print("\nID du burger à archiver: ");
        Long id = lireLong();

        if (burgerService.archiver(id)) System.out.println("✅ Burger archivé avec succès!");
        else System.out.println("❌ Burger introuvable!");
    }

    private static void listerBurgers() {
        List<Burger> burgers = burgerService.listerTous();
        System.out.println("\n📋 LISTE DES BURGERS");
        System.out.println("─────────────────────────────────────");
        for (Burger b : burgers) {
            String statut = b.isArchive() ? "[ARCHIVÉ]" : "[ACTIF]";
            System.out.printf("ID: %d | %s | %.2f FCFA %s%n", b.getId(), b.getNom(), b.getPrix(), statut);
        }
    }

    private static void ajouterMenu() {
        System.out.println("\n📝 AJOUTER UN MENU");
        System.out.print("Nom du menu: ");
        String nom = scanner.nextLine();
        System.out.print("Image (URL): ");
        String image = scanner.nextLine();
        
        listerBurgers();
        System.out.print("ID du burger: ");
        Long burgerId = lireLong();
        
        List<Complement> complements = complementService.listerActifs();
        System.out.println("\nCompléments disponibles:");
        for (Complement c : complements) {
            System.out.printf("ID: %d | %s%n", c.getId(), c.getNom());
        }
        
        System.out.print("ID de la boisson: ");
        Long boissonId = lireLong();
        System.out.print("ID des frites: ");
        Long fritesId = lireLong();
        
        Optional<Burger> burger = burgerService.trouverParId(burgerId);
        Optional<Complement> boisson = complementService.trouverParId(boissonId);
        Optional<Complement> frites = complementService.trouverParId(fritesId);
        
        if (burger.isPresent() && boisson.isPresent() && frites.isPresent()) {
            Menu menu = menuService.ajouter(nom, image, burger.get(), boisson.get(), frites.get());
            System.out.printf("✅ Menu ajouté! Prix total: %.2f FCFA%n", menu.getPrix());
        } else {
            System.out.println("❌ Éléments introuvables!");
        }
    }
    private static void modifierMenu() {
        listerMenus();
        System.out.print("\nID du menu à modifier: ");
        Long id = lireLong();
        
        Optional<Menu> opt = menuService.trouverParId(id);
        if (opt.isPresent()) {
            System.out.print("Nouveau nom: ");
            String nom = scanner.nextLine();
            System.out.print("Nouvelle image: ");
            String image = scanner.nextLine();
            
            listerBurgers();
            System.out.print("ID du burger: ");
            Long burgerId = lireLong();
            
            System.out.print("ID de la boisson: ");
            Long boissonId = lireLong();
            System.out.print("ID des frites: ");
            Long fritesId = lireLong();
            
            Optional<Burger> burger = burgerService.trouverParId(burgerId);
            Optional<Complement> boisson = complementService.trouverParId(boissonId);
            Optional<Complement> frites = complementService.trouverParId(fritesId);
            
            if (burger.isPresent() && boisson.isPresent() && frites.isPresent()) {
                menuService.modifier(id, nom, image, burger.get(), boisson.get(), frites.get());
                System.out.println("✅ Menu modifié avec succès!");
            }
        } else {
            System.out.println("❌ Menu introuvable!");
        }
    }

    private static void archiverMenu() {
        listerMenus();
        System.out.print("\nID du menu à archiver: ");
        Long id = lireLong();
        
        if (menuService.archiver(id)) {
            System.out.println("✅ Menu archivé avec succès!");
        } else {
            System.out.println("❌ Menu introuvable!");
        }
    }

    private static void listerMenus() {
        List<Menu> menus = menuService.listerTous();
        System.out.println("\n📋 LISTE DES MENUS");
        System.out.println("─────────────────────────────────────");
        for (Menu m : menus) {
            String statut = m.isArchive() ? "[ARCHIVÉ]" : "[ACTIF]";
            System.out.printf("ID: %d | %s | %.2f FCFA %s%n", 
                m.getId(), m.getNom(), m.getPrix(), statut);
        }
    }

    private static void gestionComplements() {
        while (true) {
            System.out.println("\n─────────────────────────────────────");
            System.out.println("GESTION DES COMPLÉMENTS");
            System.out.println("─────────────────────────────────────");
            System.out.println("1. Ajouter un complément");
            System.out.println("2. Modifier un complément");
            System.out.println("3. Archiver un complément");
            System.out.println("4. Lister les compléments");
            System.out.println("5. Retour");
            System.out.print("➤ Votre choix: ");
            
            int choix = lireEntier();
            
            switch (choix) {
                case 1 -> ajouterComplement();
                case 2 -> modifierComplement();
                case 3 -> archiverComplement();
                case 4 -> listerComplements();
                case 5 -> { return; }
                default -> System.out.println("❌ Choix invalide!");
            }
        }
    }

    private static void ajouterComplement() {
        System.out.println("\n📝 AJOUTER UN COMPLÉMENT");
        System.out.print("Nom: ");
        String nom = scanner.nextLine();
        System.out.print("Prix (FCFA): ");
        double prix = lireDouble();
        System.out.print("Image (URL): ");
        String image = scanner.nextLine();
        
        Complement comp = complementService.ajouter(nom, prix, image);
        System.out.println("✅ Complément ajouté avec succès! ID: " + comp.getId());
    }

    private static void modifierComplement() {
        listerComplements();
        System.out.print("\nID du complément à modifier: ");
        Long id = lireLong();
        
        Optional<Complement> opt = complementService.trouverParId(id);
        if (opt.isPresent()) {
            System.out.print("Nouveau nom: ");
            String nom = scanner.nextLine();
            System.out.print("Nouveau prix: ");
            double prix = lireDouble();
            System.out.print("Nouvelle image: ");
            String image = scanner.nextLine();
            
            complementService.modifier(id, nom, prix, image);
            System.out.println("✅ Complément modifié avec succès!");
        } else {
            System.out.println("❌ Complément introuvable!");
        }
    }

    private static void archiverComplement() {
        listerComplements();
        System.out.print("\nID du complément à archiver: ");
        Long id = lireLong();
        
        if (complementService.archiver(id)) {
            System.out.println("✅ Complément archivé avec succès!");
        } else {
            System.out.println("❌ Complément introuvable!");
        }
    }

    private static void listerComplements() {
        List<Complement> complements = complementService.listerTous();
        System.out.println("\n📋 LISTE DES COMPLÉMENTS");
        System.out.println("─────────────────────────────────────");
        for (Complement c : complements) {
            String statut = c.isArchive() ? "[ARCHIVÉ]" : "[ACTIF]";
            System.out.printf("ID: %d | %s | %.2f FCFA %s%n", 
                c.getId(), c.getNom(), c.getPrix(), statut);
        }
    }

    private static void gestionCommandes() {
        while (true) {
            System.out.println("\n─────────────────────────────────────");
            System.out.println("GESTION DES COMMANDES");
            System.out.println("─────────────────────────────────────");
            System.out.println("1. Lister toutes les commandes");
            System.out.println("2. Rechercher commandes par client");
            System.out.println("3. Annuler une commande");
            System.out.println("4. Retour");
            System.out.print("➤ Votre choix: ");
            
            int choix = lireEntier();
            
            switch (choix) {
                case 1 -> listerToutesCommandes();
                case 2 -> rechercherCommandesClient();
                case 3 -> annulerCommandeGestionnaire();
                case 4 -> { return; }
                default -> System.out.println("❌ Choix invalide!");
            }
        }
    }

    private static void listerToutesCommandes() {
        List<Commande> commandes = commandeService.listerToutesCommandes();
        afficherCommandes(commandes);
    }

    private static void rechercherCommandesClient() {
        System.out.print("Nom du client: ");
        String nom = scanner.nextLine();
        System.out.print("Prénom du client: ");
        String prenom = scanner.nextLine();
        System.out.print("Téléphone du client: ");
        String telephone = scanner.nextLine();
        
        List<Commande> commandes = commandeService.listerCommandesParClient(nom, prenom, telephone);
        afficherCommandes(commandes);
    }

    private static void annulerCommandeGestionnaire() {
        listerToutesCommandes();
        System.out.print("\nID de la commande à annuler: ");
        Long id = lireLong();
        
        if (commandeService.annulerCommande(id)) {
            System.out.println("✅ Commande annulée avec succès!");
        } else {
            System.out.println("❌ Commande introuvable!");
        }
    }

    private static void menuClient() {
        while (utilisateurConnecte != null && utilisateurConnecte.getRole() == Role.CLIENT) {
            System.out.println("\n═══════════════════════════════════════");
            System.out.println("ESPACE CLIENT - BONJOUR " + utilisateurConnecte.getPrenom().toUpperCase() + "!");
            System.out.println("═══════════════════════════════════════");
            System.out.println("1. Voir le catalogue");
            System.out.println("2. Commander");
            System.out.println("3. Mes commandes");
            System.out.println("4. Se déconnecter");
            System.out.print("➤ Votre choix: ");
            
            int choix = lireEntier();
            
            switch (choix) {
                case 1 -> afficherCatalogue();
                case 2 -> passerCommande();
                case 3 -> afficherMesCommandes();
                case 4 -> {
                    utilisateurConnecte = null;
                    clientConnecte = null;
                    com.brasilburger.security.SecurityContext.clear();
                    System.out.println("✅ Déconnexion réussie!");
                    return;
                }
                default -> System.out.println("❌ Choix invalide!");
            }
        }
    }

    private static void afficherCatalogue() {
        System.out.println("\n🍔 CATALOGUE BRASIL BURGER");
        System.out.println("═══════════════════════════════════════");
        
        System.out.println("\n🍔 BURGERS:");
        for (Burger b : burgerService.listerActifs()) {
            System.out.printf("  [%d] %s - %.2f FCFA%n", b.getId(), b.getNom(), b.getPrix());
        }
        
        System.out.println("\n🍱 MENUS:");
        for (Menu m : menuService.listerActifs()) {
            System.out.printf("  [%d] %s - %.2f FCFA%n", m.getId(), m.getNom(), m.getPrix());
            if (m.getBurger() != null) {
                System.out.println("      → " + m.getBurger().getNom());
            }
            if (m.getBoisson() != null) {
                System.out.println("      → " + m.getBoisson().getNom());
            }
            if (m.getFrites() != null) {
                System.out.println("      → " + m.getFrites().getNom());
            }
        }
        
        System.out.println("\n🍟 COMPLÉMENTS:");
        for (Complement c : complementService.listerActifs()) {
            System.out.printf("  [%d] %s - %.2f FCFA%n", c.getId(), c.getNom(), c.getPrix());
        }
    }

    private static void passerCommande() {
        System.out.println("\n🛒 NOUVELLE COMMANDE");
        System.out.println("─────────────────────────────────────");
        System.out.println("Type de commande:");
        System.out.println("1. Sur place");
        System.out.println("2. À emporter");
        System.out.println("3. Livraison");
        System.out.print("➤ Votre choix: ");
        
        int typeChoix = lireEntier();
        TypeCommande type = switch (typeChoix) {
            case 1 -> TypeCommande.SUR_PLACE;
            case 2 -> TypeCommande.A_EMPORTER;
            case 3 -> TypeCommande.LIVRAISON;
            default -> TypeCommande.SUR_PLACE;
        };
        
        Commande commande = commandeService.creerCommande(clientConnecte, type);
        
        boolean continuer = true;
        while (continuer) {
            System.out.println("\n1. Ajouter un burger");
            System.out.println("2. Ajouter un menu");
            System.out.println("3. Terminer et payer");
            System.out.print("➤ Votre choix: ");
            
            int choix = lireEntier();
            
            switch (choix) {
                case 1 -> {
                    List<Burger> burgers = burgerService.listerActifs();
                    for (Burger b : burgers) {
                        System.out.printf("[%d] %s - %.2f FCFA%n", b.getId(), b.getNom(), b.getPrix());
                    }
                    System.out.print("ID du burger: ");
                    Long burgerId = lireLong();
                    System.out.print("Quantité: ");
                    int qte = lireEntier();
                    
                    Optional<Burger> burger = burgerService.trouverParId(burgerId);
                    if (burger.isPresent()) {
                        commandeService.ajouterBurger(commande, burger.get(), qte);
                        System.out.println("✅ Burger ajouté!");
                        
                        // Proposer compléments (lecture robuste en cas d'EOF)
                        System.out.println("\nDésirez-vous des compléments? (o/n)");
                        String reponse;
                        try { reponse = scanner.nextLine(); } catch (java.util.NoSuchElementException e) { reponse = "n"; }
                        if (reponse != null && reponse.equalsIgnoreCase("o")) {
                            ajouterComplementsCommande(commande);
                        }
                    }
                }
                case 2 -> {
                    List<Menu> menus = menuService.listerActifs();
                    for (Menu m : menus) {
                        System.out.printf("[%d] %s - %.2f FCFA%n", m.getId(), m.getNom(), m.getPrix());
                    }
                    System.out.print("ID du menu: ");
                    Long menuId = lireLong();
                    System.out.print("Quantité: ");
                    int qte = lireEntier();
                    
                    Optional<Menu> menu = menuService.trouverParId(menuId);
                    if (menu.isPresent()) {
                        commandeService.ajouterMenu(commande, menu.get(), qte);
                        System.out.println("✅ Menu ajouté!");
                    }
                }
                case 3 -> {
                    continuer = false;
                    finaliserCommande(commande);
                }
            }
        }
    }

    private static void ajouterComplementsCommande(Commande commande) {
        List<Complement> complements = complementService.listerActifs();
        System.out.println("\nCompléments disponibles:");
        for (Complement c : complements) {
            System.out.printf("[%d] %s - %.2f FCFA%n", c.getId(), c.getNom(), c.getPrix());
        }

        // Permettre d'ajouter plusieurs compléments jusqu'à ce que l'utilisateur saisisse 0
        while (true) {
            System.out.print("ID du complément (0 pour terminer): ");
            Long compId = lireLong();
            if (compId <= 0) break;
            Optional<Complement> comp = complementService.trouverParId(compId);
            if (comp.isPresent()) {
                commandeService.ajouterComplement(commande, comp.get());
                System.out.println("✅ Complément ajouté!");
            } else {
                System.out.println("❌ Complément introuvable!");
            }
        }
    }

    private static void finaliserCommande(Commande commande) {
        System.out.println("\n═══════════════════════════════════════");
        System.out.println("RÉCAPITULATIF DE LA COMMANDE");
        System.out.println("═══════════════════════════════════════");
        System.out.printf("Montant total: %.2f FCFA%n", commande.getMontantTotal());
        System.out.println("\nProcéder au paiement? (o/n)");
        String payReponse;
        try { payReponse = scanner.nextLine(); } catch (java.util.NoSuchElementException e) { payReponse = "n"; }
        if (payReponse != null && payReponse.equalsIgnoreCase("o")) {
            if (commandeService.payerCommande(commande.getId())) {
                System.out.println("✅ Commande payée et validée!");
                System.out.println("📦 Numéro de commande: " + commande.getId());
            }
        } else {
            commandeService.annulerCommande(commande.getId());
            System.out.println("❌ Commande annulée");
        }
    }

    private static void afficherMesCommandes() {
        List<Commande> commandes = commandeService.listerCommandesClient(clientConnecte.getId());
        afficherCommandes(commandes);
    }

    private static void afficherCommandes(List<Commande> commandes) {
        if (commandes.isEmpty()) {
            System.out.println("\n📭 Aucune commande trouvée");
            return;
        }
        
        System.out.println("\n📋 COMMANDES");
        System.out.println("═══════════════════════════════════════");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        
        for (Commande cmd : commandes) {
            System.out.printf("\n🆔 Commande #%d%n", cmd.getId());
            System.out.printf("👤 Client: %s%n", cmd.getClient());
            System.out.printf("📅 Date: %s%n", cmd.getDateCommande().format(formatter));
            System.out.printf("📍 Type: %s%n", cmd.getTypeCommande().getLibelle());
            System.out.printf("📊 Statut: %s%n", cmd.getStatut().getLibelle());
            System.out.printf("💰 Montant: %.2f FCFA%n", cmd.getMontantTotal());
            System.out.println("─────────────────────────────────────");
        }
    }

    private static void initialiserDonnees() {
        // Créer des utilisateurs gestionnaires
        userService.creerUtilisateur("Admin", "Gestionnaire", "admin@brasilburger.com", "admin123", Role.GESTIONNAIRE);
        userService.creerUtilisateur("Silva", "Maria", "maria@brasilburger.com", "maria123", Role.GESTIONNAIRE);
        // Créer un compte client de démonstration
        Client demoClient = clientService.creerCompte("Dupont", "Jean", "77000000", "jean@client.com", "jean123");
        System.out.println("   → Compte client créé: email=jean@client.com | mot de passe=jean123");
        // Utiliser le compte gestionnaire pour initialiser les données protégées
        com.brasilburger.model.User admin = userService.trouverParEmail("admin@brasilburger.com").orElse(null);
        com.brasilburger.security.SecurityContext.setCurrentUser(admin);
        
        // Burgers
        burgerService.ajouter("Brasil Classic", 3500, "brasil_classic.jpg");
        burgerService.ajouter("Chicken Deluxe", 4000, "chicken_deluxe.jpg");
        burgerService.ajouter("Veggie Burger", 3000, "veggie.jpg");
        burgerService.ajouter("Double Cheese", 5000, "double_cheese.jpg");
        
        // Compléments
        complementService.ajouter("Frites", 1000, "frites.jpg");
        complementService.ajouter("Coca Cola", 500, "coca.jpg");
        complementService.ajouter("Jus d'orange", 800, "jus.jpg");
        complementService.ajouter("Eau minérale", 300, "eau.jpg");
        
        // Menus
        Burger b1 = burgerService.trouverParId(1L).orElse(null);
        Complement frites = complementService.trouverParId(1L).orElse(null);
        Complement coca = complementService.trouverParId(2L).orElse(null);
        
        if (b1 != null && frites != null && coca != null) {
            menuService.ajouter("Menu Brasil Classic", "menu_classic.jpg", b1, coca, frites);
            menuService.ajouter("Menu Chicken", "menu_chicken.jpg", 
                burgerService.trouverParId(2L).orElse(null), coca, frites);
        }
        com.brasilburger.security.SecurityContext.clear();
        
        System.out.println("\n📊 Données initialisées:");
        System.out.println("   → 2 comptes gestionnaires créés");
        System.out.println("   → Email: admin@brasilburger.com | Mot de passe: admin123");
        System.out.println("   → Email: maria@brasilburger.com | Mot de passe: maria123");
    }

    private static int lireEntier() {
        try {
            String line = scanner.nextLine();
            return Integer.parseInt(line);
        } catch (NumberFormatException e) {
            return -1;
        } catch (java.util.NoSuchElementException e) {
            return -1;
        }
    }

    private static long lireLong() {
        try {
            String line = scanner.nextLine();
            return Long.parseLong(line);
        } catch (NumberFormatException e) {
            return -1L;
        } catch (java.util.NoSuchElementException e) {
            return -1L;
        }
    }

    private static double lireDouble() {
        try {
            String line = scanner.nextLine();
            return Double.parseDouble(line);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
}