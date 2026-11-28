package com.smartcampus.backend.Repository;

import com.smartcampus.backend.entity.Etudiant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EtudiantRepository extends JpaRepository<Etudiant, Long> {
    boolean existsByMatricule(String matricule);

    @Query("""
        SELECT e FROM Etudiant e
        LEFT JOIN e.filiere f
        WHERE (:search IS NULL OR :search = ''
               OR LOWER(e.user.nom) LIKE LOWER(CONCAT('%', :search, '%'))
               OR LOWER(e.user.prenom) LIKE LOWER(CONCAT('%', :search, '%'))
               OR LOWER(e.matricule) LIKE LOWER(CONCAT('%', :search, '%'))
               OR LOWER(f.code) LIKE LOWER(CONCAT('%', :search, '%'))
               OR LOWER(f.nom) LIKE LOWER(CONCAT('%', :search, '%')))
          AND (:filiereId IS NULL OR f.id = :filiereId)
    """)
    List<Etudiant> searchEtudiants(@Param("search") String search,
                                   @Param("filiereId") Long filiereId);
}