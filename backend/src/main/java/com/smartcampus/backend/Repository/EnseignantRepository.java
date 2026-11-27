package com.smartcampus.backend.Repository;
import com.smartcampus.backend.entity.Enseignant;
import org.springframework.data.jpa.repository.JpaRepository;
public interface EnseignantRepository extends JpaRepository<Enseignant, Long> {
}