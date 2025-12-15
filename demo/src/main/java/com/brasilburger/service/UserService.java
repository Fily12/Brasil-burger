package com.brasilburger.service;

import com.brasilburger.model.User;
import com.brasilburger.model.enums.Role;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserService {
    private final List<User> users = new ArrayList<>();
    private long nextId = 1;

    public User creerUtilisateur(String nom, String prenom, String email, String motDePasse, Role role) {
        if (trouverParEmail(email).isPresent()) throw new RuntimeException("Email déjà utilisé");
        User u = new User(nextId++, nom, prenom, email, motDePasse, role);
        users.add(u);
        return u;
    }

    public Optional<User> authentifier(String email, String motDePasse) {
        return users.stream().filter(u -> u.getEmail().equalsIgnoreCase(email) && u.getMotDePasse().equals(motDePasse)).findFirst();
    }

    public Optional<User> trouverParEmail(String email) {
        return users.stream().filter(u -> u.getEmail().equalsIgnoreCase(email)).findFirst();
    }

    public Optional<User> trouverParId(Long id) {
        return users.stream().filter(u -> u.getId().equals(id)).findFirst();
    }
}
