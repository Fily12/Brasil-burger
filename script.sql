CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(100),
    prenom VARCHAR(100),
    telephone VARCHAR(20),
    email VARCHAR(150) UNIQUE,
    password VARCHAR(255),
    role VARCHAR(30) CHECK (role IN ('CLIENT','GESTIONNAIRE','LIVREUR')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE burgers (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(100),
    prix NUMERIC(10,2),
    image VARCHAR(255),
    archive BOOLEAN DEFAULT FALSE
);
CREATE TABLE complements (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(100),
    prix NUMERIC(10,2),
    image VARCHAR(255),
    archive BOOLEAN DEFAULT FALSE
);
CREATE TABLE menus (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(100),
    image VARCHAR(255),
    archive BOOLEAN DEFAULT FALSE
);
CREATE TABLE menu_burgers (
    menu_id INT REFERENCES menus(id),
    burger_id INT REFERENCES burgers(id),
    PRIMARY KEY (menu_id, burger_id)
);
CREATE TABLE commandes (
    id SERIAL PRIMARY KEY,
    client_id INT REFERENCES users(id),
    type_commande VARCHAR(30) CHECK (type_commande IN ('SUR_PLACE','A_EMPORTER','LIVRAISON')),
    etat VARCHAR(30) CHECK (etat IN ('EN_COURS','VALIDEE','TERMINEE','ANNULEE')),
    total NUMERIC(10,2),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE paiements (
    id SERIAL PRIMARY KEY,
    commande_id INT UNIQUE REFERENCES commandes(id),
    montant NUMERIC(10,2),
    mode_paiement VARCHAR(20) CHECK (mode_paiement IN ('WAVE','OM')),
    date_paiement TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE zones (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(100),
    prix NUMERIC(10,2)
);
CREATE TABLE livraisons (
    id SERIAL PRIMARY KEY,
    commande_id INT REFERENCES commandes(id),
    livreur_id INT REFERENCES users(id),
    zone_id INT REFERENCES zones(id)
);
