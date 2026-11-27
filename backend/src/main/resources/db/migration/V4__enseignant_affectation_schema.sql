CREATE TABLE enseignant (
    id BIGINT PRIMARY KEY,
    specialite VARCHAR(150) NOT NULL,
    grade VARCHAR(100),
    CONSTRAINT fk_enseignant_user FOREIGN KEY (id) REFERENCES utilisateur(id) ON DELETE CASCADE
);

CREATE TABLE affectation (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    enseignant_id BIGINT NOT NULL,
    module_id BIGINT NOT NULL,
    annee_universitaire VARCHAR(20) NOT NULL,
    CONSTRAINT fk_affectation_enseignant FOREIGN KEY (enseignant_id) REFERENCES enseignant(id) ON DELETE CASCADE,
    CONSTRAINT fk_affectation_module FOREIGN KEY (module_id) REFERENCES module(id) ON DELETE CASCADE,
    CONSTRAINT uq_affectation UNIQUE (enseignant_id, module_id, annee_universitaire)
);