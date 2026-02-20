package xyz.fdt.ciolaflixbe.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.fdt.ciolaflixbe.model.Media;

import java.util.Optional;

@Repository
public interface MediaRepo extends JpaRepository<Media, Long> {

    Optional<Media> findByTmdbId(String tmdbId);

    boolean existsByTmdbId(String tmdbId);
}
