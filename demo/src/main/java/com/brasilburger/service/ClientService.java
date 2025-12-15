package com.brasilburger.service;

import com.brasilburger.model.Client;
import com.brasilburger.model.User;
import com.brasilburger.model.enums.Role;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClientService {
    private final UserService userService;
    private final List<Client> clients = new ArrayList<>();
    private long nextId = 1;

    public ClientService(UserService userService) {
        this.userService = userService;
    }

    public Client creerCompte(String nom, String prenom, String telephone, String email, String motDePasse) {
        User u = userService.creerUtilisateur(nom, prenom, email, motDePasse, Role.CLIENT);
        Client c = new Client(nextId++, u.getId(), nom, prenom, telephone);
        clients.add(c);
        return c;
    }

    public Optional<Client> trouverParUserId(Long userId) {
        return clients.stream().filter(c -> c.getUserId().equals(userId)).findFirst();
    }

    public Optional<Client> trouverParId(Long id) {
        return clients.stream().filter(c -> c.getId().equals(id)).findFirst();
    }
}
