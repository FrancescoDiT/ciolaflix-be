package xyz.fdt.ciolaflixbe.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.fdt.ciolaflixbe.model.watchLater.WatchLater;
import xyz.fdt.ciolaflixbe.model.watchLater.WatchLaterId;

import java.util.List;
import java.util.Optional;

@Repository
public interface WatchLaterRepo extends JpaRepository<WatchLater, WatchLaterId> {

    List<WatchLater> findByUserId(Long userId);

    Optional<WatchLater> findByUserIdAndMediaId(Long userId, Long mediaId);

    boolean existsByUserIdAndMediaId(Long userId, Long mediaId);

    void deleteByUserIdAndMediaId(Long userId, Long mediaId);
}
