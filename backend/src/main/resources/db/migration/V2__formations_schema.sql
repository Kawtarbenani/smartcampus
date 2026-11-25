CREATE TABLE filiere (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(30) NOT NULL UNIQUE,
    nom VARCHAR(150) NOT NULL,
    description VARCHAR(255)
);

CREATE TABLE semestre (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(20) NOT NULL UNIQUE,
    libelle VARCHAR(100) NOT NULL,
    annee_universitaire VARCHAR(20) NOT NULL
);

CREATE TABLE module (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(30) NOT NULL UNIQUE,
    nom VARCHAR(150) NOT NULL,
    credits INT NOT NULL,
    volume_horaire INT NOT NULL,
    filiere_id BIGINT NOT NULL,
    semestre_id BIGINT NOT NULL,
    CONSTRAINT fk_module_filiere FOREIGN KEY (filiere_id) REFERENCES filiere(id) ON DELETE CASCADE,
    CONSTRAINT fk_module_semestre FOREIGN KEY (semestre_id) REFERENCES semestre(id) ON DELETE CASCADE
);