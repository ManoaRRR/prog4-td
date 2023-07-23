CREATE TABLE entreprise if not exist (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    slogan VARCHAR(255),
    adresse VARCHAR(255),
    email VARCHAR(255),
    nif VARCHAR(255),
    stat VARCHAR(255),
    rcs VARCHAR(255),
    logo TEXT
);
