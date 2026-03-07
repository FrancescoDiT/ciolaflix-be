package xyz.fdt.ciolaflixbe.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import xyz.fdt.ciolaflixbe.model.continueWatching.ContinueWatching;
import xyz.fdt.ciolaflixbe.model.continueWatching.ContinueWatchingId;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ContinueWatchingRepo extends JpaRepository<ContinueWatching, ContinueWatchingId> {

    List<ContinueWatching> findByCiolaManIdOrderByUpdatedAtDesc(Long userId);

    Optional<ContinueWatching> findByCiolaManIdAndMediaId(Long userId, Long mediaId);

    boolean existsByCiolaManIdAndMediaId(Long userId, Long mediaId);

    void deleteByCiolaManIdAndMediaId(Long userId, Long mediaId);

}
