package xyz.fdt.ciolaflixbe.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WatchLaterRepo extends JpaRepository<WatchLater, Long> {
    List<WatchLater> findByCiolaMenId(Long ciolaMenId);
}
