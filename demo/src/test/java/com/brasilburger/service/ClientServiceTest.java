package com.brasilburger.service;

import com.brasilburger.model.Client;
import com.brasilburger.model.User;
import com.brasilburger.model.enums.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ClientServiceTest {
    private final UserService userService = new UserService();
    private final ClientService clientService = new ClientService(userService);

    @Test
    public void creerCompteClientEtAuthentifier() {
        Client c = clientService.creerCompte("Dupont", "Jean", "77000000", "jean@client.com", "jean123");
        Assertions.assertNotNull(c);
        User u = userService.authentifier("jean@client.com", "jean123").orElse(null);
        Assertions.assertNotNull(u);
        Assertions.assertEquals(Role.CLIENT, u.getRole());
        Assertions.assertTrue(clientService.trouverParUserId(u.getId()).isPresent());
    }
}
