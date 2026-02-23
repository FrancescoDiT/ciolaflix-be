package xyz.fdt.ciolaflixbe.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.fdt.ciolaflixbe.model.continueWatching.ContinueWatching;
import xyz.fdt.ciolaflixbe.model.continueWatching.ContinueWatchingId;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContinueWatchingRepo extends JpaRepository<ContinueWatching, ContinueWatchingId> {

    List<ContinueWatching> findByUserIdOrderByUpdatedAtDesc(Long userId);

    Optional<ContinueWatching> findByUserIdAndMediaId(Long userId, Long mediaId);

    boolean existsByUserIdAndMediaId(Long userId, Long mediaId);

    void deleteByUserIdAndMediaId(Long userId, Long mediaId);
}
