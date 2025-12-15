package com.brasilburger.service;

import com.brasilburger.model.Complement;
import com.brasilburger.model.Burger;
import com.brasilburger.model.enums.Role;
import com.brasilburger.security.SecurityContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MenuServiceTest {
    private final MenuService menuService = new MenuService();

    @AfterEach
    public void tearDown() { SecurityContext.clear(); }

    @Test
    public void ajouterSansAuthDevraitEchouer() {
        Assertions.assertThrows(SecurityException.class, () -> menuService.ajouter("Test", "img.jpg", new Burger(1L, "B", 1000, "b.jpg"), new Complement(1L, "C", 200, "c.jpg"), new Complement(2L, "F", 300, "f.jpg")));
    }

    @Test
    public void ajouterAvecGestionnaireDevraitReussir() {
        com.brasilburger.model.User admin = new com.brasilburger.model.User(1L, "A", "B", "a@x.com", "p", Role.GESTIONNAIRE);
        SecurityContext.setCurrentUser(admin);
        var m = menuService.ajouter("Menu Test", "menu.jpg", new Burger(1L, "B", 1000, "b.jpg"), new Complement(1L, "C", 200, "c.jpg"), new Complement(2L, "F", 300, "f.jpg"));
        Assertions.assertNotNull(m);
        Assertions.assertEquals("Menu Test", m.getNom());
        Assertions.assertEquals(1500.0, m.getPrix());
    }

    @Test
    public void listerTousEtActifsDevraitFonctionner() {
        com.brasilburger.model.User admin = new com.brasilburger.model.User(1L, "A", "B", "a@x.com", "p", Role.GESTIONNAIRE);
        SecurityContext.setCurrentUser(admin);
        menuService.ajouter("M1", "m1.jpg", new Burger(1L, "B1", 1000, "b1.jpg"), new Complement(1L, "C1", 100, "c1.jpg"), new Complement(2L, "F1", 200, "f1.jpg"));
        menuService.ajouter("M2", "m2.jpg", new Burger(2L, "B2", 1100, "b2.jpg"), new Complement(3L, "C2", 150, "c2.jpg"), new Complement(4L, "F2", 250, "f2.jpg"));
        var all = menuService.listerTous();
        Assertions.assertEquals(2, all.size());
        // archiver un menu
        menuService.archiver(all.get(0).getId());
        var actifs = menuService.listerActifs();
        Assertions.assertEquals(1, actifs.size());
    }

    @Test
    public void modifierMenuDevraitFonctionnerEtEtreProtege() {
        com.brasilburger.model.User admin = new com.brasilburger.model.User(1L, "A", "B", "a@x.com", "p", Role.GESTIONNAIRE);
        SecurityContext.setCurrentUser(admin);
        var m = menuService.ajouter("Orig", "o.jpg", new Burger(1L, "B1", 1000, "b1.jpg"), new Complement(1L, "C1", 100, "c1.jpg"), new Complement(2L, "F1", 200, "f1.jpg"));
        // clearance removes auth
        SecurityContext.clear();
        Assertions.assertThrows(SecurityException.class, () -> menuService.modifier(m.getId(), "New", "n.jpg", new Burger(2L, "B2", 1200, "b2.jpg"), new Complement(3L, "C2", 150, "c2.jpg"), new Complement(4L, "F2", 250, "f2.jpg")));

        // set admin again and modify
        SecurityContext.setCurrentUser(admin);
        menuService.modifier(m.getId(), "New", "n.jpg", new Burger(2L, "B2", 1200, "b2.jpg"), new Complement(3L, "C2", 150, "c2.jpg"), new Complement(4L, "F2", 250, "f2.jpg"));
        var after = menuService.trouverParId(m.getId()).get();
        Assertions.assertEquals("New", after.getNom());
        Assertions.assertEquals(1600.0, after.getPrix());
    }

    @Test
    public void supprimerMenuDevraitEtreProtegeEtSupprimer() {
        com.brasilburger.model.User admin = new com.brasilburger.model.User(1L, "A", "B", "a@x.com", "p", Role.GESTIONNAIRE);
        SecurityContext.setCurrentUser(admin);
        var m = menuService.ajouter("ToDel", "d.jpg", new Burger(1L, "B1", 500, "b1.jpg"), new Complement(1L, "C1", 50, "c1.jpg"), new Complement(2L, "F1", 100, "f1.jpg"));
        // clearance removes auth
        SecurityContext.clear();
        Assertions.assertThrows(SecurityException.class, () -> menuService.supprimer(m.getId()));

        SecurityContext.setCurrentUser(admin);
        boolean done = menuService.supprimer(m.getId());
        Assertions.assertTrue(done);
        Assertions.assertTrue(menuService.trouverParId(m.getId()).isEmpty());
    }
}
