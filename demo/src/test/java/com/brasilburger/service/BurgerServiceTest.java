package com.brasilburger.service;

import com.brasilburger.model.enums.Role;
import com.brasilburger.security.SecurityContext;
import com.brasilburger.security.SecurityUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BurgerServiceTest {
    private final BurgerService burgerService = new BurgerService();

    @AfterEach
    public void tearDown() { SecurityContext.clear(); }

    @Test
    public void ajouterSansAuthDevraitEchouer() {
        Assertions.assertThrows(SecurityException.class, () -> burgerService.ajouter("Test", 1000, "img.jpg"));
    }

    @Test
    public void ajouterAvecGestionnaireDevraitReussir() {
        com.brasilburger.model.User admin = new com.brasilburger.model.User(1L, "A", "B", "a@x.com", "p", Role.GESTIONNAIRE);
        SecurityContext.setCurrentUser(admin);
        var b = burgerService.ajouter("OK", 1000, "img.jpg");
        Assertions.assertNotNull(b);
        Assertions.assertEquals("OK", b.getNom());
    }
}
