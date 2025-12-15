package com.brasilburger.service;

import com.brasilburger.model.Client;
import com.brasilburger.model.User;
import com.brasilburger.model.enums.Role;
import com.brasilburger.security.SecurityContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandeServiceTest {
    private final CommandeService commandeService = new CommandeService();

    @AfterEach
    public void tearDown() { SecurityContext.clear(); }

    @Test
    public void clientPeutAnnulerSaCommande() {
        User u = new User(1L, "Nom", "P", "c@x.com", "p", Role.CLIENT);
        Client c = new Client(1L, 1L, "Nom", "P", "000");
        var cmd = commandeService.creerCommande(c, com.brasilburger.model.enums.TypeCommande.SUR_PLACE);
        SecurityContext.setCurrentUser(u);
        boolean ok = commandeService.annulerCommande(cmd.getId());
        Assertions.assertTrue(ok);
    }

    @Test
    public void clientNePeutAnnulerCommandeAutre() {
        User u = new User(1L, "Nom", "P", "c@x.com", "p", Role.CLIENT);
        Client c = new Client(1L, 2L, "Nom", "P", "000");
        var cmd = commandeService.creerCommande(c, com.brasilburger.model.enums.TypeCommande.SUR_PLACE);
        SecurityContext.setCurrentUser(u);
        boolean ok = commandeService.annulerCommande(cmd.getId());
        Assertions.assertFalse(ok);
    }

    @Test
    public void gestionnairePeutAnnulerToutesCommandes() {
        User g = new User(99L, "G", "M", "g@x.com", "p", Role.GESTIONNAIRE);
        Client c = new Client(1L, 2L, "Nom", "P", "000");
        var cmd = commandeService.creerCommande(c, com.brasilburger.model.enums.TypeCommande.SUR_PLACE);
        SecurityContext.setCurrentUser(g);
        boolean ok = commandeService.annulerCommande(cmd.getId());
        Assertions.assertTrue(ok);
    }
}
