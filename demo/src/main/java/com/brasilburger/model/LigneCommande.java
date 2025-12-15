package com.brasilburger.model;

public class LigneCommande {
    private Long id;
    private Burger burger;
    private Menu menu;
    private Complement complement;
    private int quantite = 1;

    public LigneCommande() {}

    public LigneCommande(Burger burger, int quantite) {
        this.burger = burger;
        this.quantite = quantite;
    }

    public LigneCommande(Menu menu, int quantite) {
        this.menu = menu;
        this.quantite = quantite;
    }

    public LigneCommande(Complement complement) {
        this.complement = complement;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Burger getBurger() { return burger; }
    public Menu getMenu() { return menu; }
    public Complement getComplement() { return complement; }
    public int getQuantite() { return quantite; }

    public double getMontant() {
        if (burger != null) return burger.getPrix() * quantite;
        if (menu != null) return menu.getPrix() * quantite;
        if (complement != null) return complement.getPrix();
        return 0.0;
    }
}
