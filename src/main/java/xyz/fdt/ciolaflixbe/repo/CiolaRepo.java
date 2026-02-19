package xyz.fdt.ciolaflixbe.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.fdt.ciolaflixbe.model.CiolaMan;

import java.util.Optional;

@Repository
public interface CiolaRepo extends JpaRepository<CiolaMan, Long> {
    Optional<CiolaMan> findByAuthAccountId(Long id);
}
