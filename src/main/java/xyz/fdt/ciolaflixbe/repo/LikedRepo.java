package xyz.fdt.ciolaflixbe.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikedRepo extends JpaRepository<Liked, Long> {
    Optional<Liked> findByMediaId(String mediaId);
}
