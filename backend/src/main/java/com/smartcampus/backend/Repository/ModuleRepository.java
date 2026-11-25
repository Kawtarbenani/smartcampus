package com.smartcampus.backend.Repository;
import com.smartcampus.backend.entity.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface ModuleRepository extends JpaRepository<Module, Long> {
    boolean existsByCode(String code);
    List<Module> findByFiliereId(Long filiereId);
    List<Module> findBySemestreId(Long semestreId);
}