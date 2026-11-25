package com.smartcampus.backend.Repository;
import com.smartcampus.backend.entity.Semestre;
import org.springframework.data.jpa.repository.JpaRepository;
public interface SemestreRepository extends JpaRepository<Semestre, Long> {
    boolean existsByCode(String code);
}