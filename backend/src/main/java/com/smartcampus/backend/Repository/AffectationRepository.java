package com.smartcampus.backend.Repository;
import com.smartcampus.backend.entity.Affectation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface AffectationRepository extends JpaRepository<Affectation, Long> {
    boolean existsByEnseignantIdAndModuleIdAndAnneeUniversitaire(Long enseignantId, Long moduleId, String anneeUniversitaire);
    List<Affectation> findByEnseignantId(Long enseignantId);
}