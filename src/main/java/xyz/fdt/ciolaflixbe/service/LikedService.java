package xyz.fdt.ciolaflixbe.service;

import it.trinex.blackout.security.BlackoutUserPrincipal;
import it.trinex.blackout.service.CurrentUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.fdt.ciolaflixbe.dto.LikedResponse;
import xyz.fdt.ciolaflixbe.mapper.LikedMapper;
import xyz.fdt.ciolaflixbe.model.CiolaMan;
import xyz.fdt.ciolaflixbe.model.Liked;
import xyz.fdt.ciolaflixbe.model.Media;
import xyz.fdt.ciolaflixbe.repo.CiolaRepo;
import xyz.fdt.ciolaflixbe.repo.LikedRepo;
import xyz.fdt.ciolaflixbe.repo.MediaRepo;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class LikedService {

    private final LikedRepo likedRepo;
    private final CiolaRepo ciolaRepo;
    private final MediaRepo mediaRepo;
    private final CurrentUserService<BlackoutUserPrincipal> currentUserService;
    private final LikedMapper likedMapper;

    public void addLiked(String mediaId) {
        Long currentUserId = currentUserService.getCurrentPrincipal().getUserId();

        CiolaMan user = ciolaRepo.findById(currentUserId)
                .orElseThrow(() -> new NoSuchElementException("User not found with id: " + currentUserId));

        Media media = mediaRepo.findByTmdbId(mediaId)
                .orElseGet(() -> createMedia(mediaId));

        if (likedRepo.existsByUserIdAndMediaId(user.getId(), media.getId())) {
            throw new IllegalStateException("Media already liked by user");
        }

        Liked liked = new Liked(user, media);
        likedRepo.save(liked);
    }

    public void deleteLiked(String mediaId) {
        Long currentUserId = currentUserService.getCurrentPrincipal().getUserId();

        CiolaMan user = ciolaRepo.findById(currentUserId)
                .orElseThrow(() -> new NoSuchElementException("User not found with id: " + currentUserId));

        Media media = mediaRepo.findByTmdbId(mediaId)
                .orElseThrow(() -> new NoSuchElementException("Media not found with id: " + mediaId));

        if (!likedRepo.existsByUserIdAndMediaId(user.getId(), media.getId())) {
            throw new NoSuchElementException("Media not liked by user");
        }

        likedRepo.deleteByUserIdAndMediaId(user.getId(), media.getId());
    }

    public LikedResponse getLikedMediaIds() {
        Long currentUserId = currentUserService.getCurrentPrincipal().getUserId();

        List<Liked> likedList = likedRepo.findByUserId(currentUserId);
        List<String> mediaIds = likedMapper.toMediaIdList(likedList);

        return LikedResponse.builder()
                .mediaIds(mediaIds)
                .build();
    }

    private Media createMedia(String tmdbId) {
        Media media = Media.builder()
                .tmdbId(tmdbId)
                .build();
        return mediaRepo.save(media);
    }
}
