package com.brasilburger.service;

import com.brasilburger.model.Menu;
import com.brasilburger.model.Burger;
import com.brasilburger.model.Complement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MenuService {
    private final List<Menu> menus = new ArrayList<>();
    private long nextId = 1;

    public Menu ajouter(String nom, String image, Burger burger, Complement boisson, Complement frites) {
        com.brasilburger.security.SecurityUtils.requireRole(com.brasilburger.model.enums.Role.GESTIONNAIRE);
        Menu m = new Menu(nextId++, nom, image, burger, boisson, frites);
        menus.add(m);
        return m;
    }

    public Optional<Menu> trouverParId(Long id) {
        return menus.stream().filter(m -> m.getId().equals(id)).findFirst();
    }

    public List<Menu> listerTous() { return new ArrayList<>(menus); }

    public List<Menu> listerActifs() {
        List<Menu> out = new ArrayList<>();
        for (Menu m : menus) if (!m.isArchive()) out.add(m);
        return out;
    }

    public boolean archiver(Long id) {
        com.brasilburger.security.SecurityUtils.requireRole(com.brasilburger.model.enums.Role.GESTIONNAIRE);
        Optional<Menu> opt = trouverParId(id);
        if (opt.isPresent()) { opt.get().setArchive(true); return true; }
        return false;
    }

    public void modifier(Long id, String nom, String image, Burger burger, Complement boisson, Complement frites) {
        com.brasilburger.security.SecurityUtils.requireRole(com.brasilburger.model.enums.Role.GESTIONNAIRE);
        trouverParId(id).ifPresent(m -> { m.setNom(nom); m.setImage(image); m.setBurger(burger); m.setBoisson(boisson); m.setFrites(frites); });
    }

    public boolean supprimer(Long id) {
        com.brasilburger.security.SecurityUtils.requireRole(com.brasilburger.model.enums.Role.GESTIONNAIRE);
        Optional<Menu> opt = trouverParId(id);
        if (opt.isPresent()) { return menus.remove(opt.get()); }
        return false;
    }
}
