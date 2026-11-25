CREATE TABLE etudiant (
    id BIGINT PRIMARY KEY,
    matricule VARCHAR(50) NOT NULL UNIQUE,
    annee INT NOT NULL,
    filiere_id BIGINT,
    CONSTRAINT fk_etudiant_user FOREIGN KEY (id) REFERENCES utilisateur(id) ON DELETE CASCADE,
    CONSTRAINT fk_etudiant_filiere FOREIGN KEY (filiere_id) REFERENCES filiere(id) ON DELETE SET NULL
);