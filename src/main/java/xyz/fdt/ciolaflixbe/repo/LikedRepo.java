package xyz.fdt.ciolaflixbe.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.fdt.ciolaflixbe.model.liked.Liked;
import xyz.fdt.ciolaflixbe.model.liked.LikedId;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikedRepo extends JpaRepository<Liked, LikedId> {

    List<Liked> findByCiolaManId(Long ciolaManId);

    Optional<Liked> findByCiolaManIdAndMediaId(Long ciolaManId, Long mediaId);

    boolean existsByCiolaManIdAndMediaId(Long ciolaManId, Long mediaId);

    void deleteByCiolaManIdAndMediaId(Long ciolaManId, Long mediaId);
}
