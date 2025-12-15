package com.brasilburger.service;

import com.brasilburger.model.Complement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ComplementService {
    private final List<Complement> comps = new ArrayList<>();
    private long nextId = 1;

    public Complement ajouter(String nom, double prix, String image) {
        com.brasilburger.security.SecurityUtils.requireRole(com.brasilburger.model.enums.Role.GESTIONNAIRE);
        Complement c = new Complement(nextId++, nom, prix, image);
        comps.add(c);
        return c;
    }

    public Optional<Complement> trouverParId(Long id) {
        return comps.stream().filter(c -> c.getId().equals(id)).findFirst();
    }

    public List<Complement> listerTous() { return new ArrayList<>(comps); }

    public List<Complement> listerActifs() {
        List<Complement> out = new ArrayList<>();
        for (Complement c : comps) if (!c.isArchive()) out.add(c);
        return out;
    }

    public boolean archiver(Long id) {
        com.brasilburger.security.SecurityUtils.requireRole(com.brasilburger.model.enums.Role.GESTIONNAIRE);
        Optional<Complement> opt = trouverParId(id);
        if (opt.isPresent()) { opt.get().setArchive(true); return true; }
        return false;
    }

    public void modifier(Long id, String nom, double prix, String image) {
        com.brasilburger.security.SecurityUtils.requireRole(com.brasilburger.model.enums.Role.GESTIONNAIRE);
        trouverParId(id).ifPresent(c -> { c.setNom(nom); c.setPrix(prix); c.setImage(image); });
    }
}
