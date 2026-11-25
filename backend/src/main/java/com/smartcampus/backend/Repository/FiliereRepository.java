package com.smartcampus.backend.Repository;
import com.smartcampus.backend.entity.Filiere;
import org.springframework.data.jpa.repository.JpaRepository;
public interface FiliereRepository extends JpaRepository<Filiere, Long> {
    boolean existsByCode(String code);
}