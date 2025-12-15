package com.brasilburger.service;

import com.brasilburger.model.Burger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BurgerService {
    private final List<Burger> burgers = new ArrayList<>();
    private long nextId = 1;

    public Burger ajouter(String nom, double prix, String image) {
        com.brasilburger.security.SecurityUtils.requireRole(com.brasilburger.model.enums.Role.GESTIONNAIRE);
        Burger b = new Burger(nextId++, nom, prix, image);
        burgers.add(b);
        return b;
    }

    public Optional<Burger> trouverParId(Long id) {
        return burgers.stream().filter(b -> b.getId().equals(id)).findFirst();
    }

    public List<Burger> listerTous() { return new ArrayList<>(burgers); }

    public List<Burger> listerActifs() {
        List<Burger> out = new ArrayList<>();
        for (Burger b : burgers) if (!b.isArchive()) out.add(b);
        return out;
    }

    public boolean archiver(Long id) {
        com.brasilburger.security.SecurityUtils.requireRole(com.brasilburger.model.enums.Role.GESTIONNAIRE);
        Optional<Burger> opt = trouverParId(id);
        if (opt.isPresent()) { opt.get().setArchive(true); return true; }
        return false;
    }

    public void modifier(Long id, String nom, double prix, String image) {
        com.brasilburger.security.SecurityUtils.requireRole(com.brasilburger.model.enums.Role.GESTIONNAIRE);
        trouverParId(id).ifPresent(b -> { b.setNom(nom); b.setPrix(prix); b.setImage(image); });
    }
}
