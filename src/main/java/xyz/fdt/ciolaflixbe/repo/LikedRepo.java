package xyz.fdt.ciolaflixbe.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.fdt.ciolaflixbe.model.Liked;
import xyz.fdt.ciolaflixbe.model.LikedId;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikedRepo extends JpaRepository<Liked, LikedId> {

    List<Liked> findByUserId(Long userId);

    Optional<Liked> findByUserIdAndMediaId(Long userId, Long mediaId);

    boolean existsByUserIdAndMediaId(Long userId, Long mediaId);

    void deleteByUserIdAndMediaId(Long userId, Long mediaId);
}
