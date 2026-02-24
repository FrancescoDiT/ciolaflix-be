package xyz.fdt.ciolaflixbe.service;

import it.trinex.blackout.security.BlackoutUserPrincipal;
import it.trinex.blackout.service.CurrentUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.fdt.ciolaflixbe.dto.request.MediaRequest;
import xyz.fdt.ciolaflixbe.exception.user.UserNotFoundException;
import xyz.fdt.ciolaflixbe.model.CiolaMan;
import xyz.fdt.ciolaflixbe.model.Media;
import xyz.fdt.ciolaflixbe.model.MediaType;
import xyz.fdt.ciolaflixbe.model.continueWatching.ContinueWatching;
import xyz.fdt.ciolaflixbe.repo.CiolaRepo;
import xyz.fdt.ciolaflixbe.repo.ContinueWatchingRepo;
import xyz.fdt.ciolaflixbe.repo.MediaRepo;
import xyz.fdt.ciolaflixbe.utils.WebClientUtil;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class ContinueWatchingService {

    private final ContinueWatchingRepo continueWatchingRepo;
    private final CiolaRepo ciolaRepo;
    private final MediaRepo mediaRepo;
    private final CurrentUserService<BlackoutUserPrincipal> currentUserService;
    private final WebClientUtil webClientUtil;
    private final PoolService poolService;

    /**
     * Adds or updates a continue watching entry in a single transaction.
     * Uses optimized upsert query that handles both insert and update.
     */
    @Transactional
    public void addContinueWatching(MediaRequest request) {
        String mediaId = request.getMediaId();
        String mediaType = request.getMediaType();
        String timestamp = request.getTimestamp();

        // Validate media exists in TMDB
        webClientUtil.checkMediaExists(mediaId, MediaType.valueOf(mediaType.toUpperCase()));

        // Get current user
        Long currentUserId = currentUserService.getCurrentPrincipal().getUserId();
        CiolaMan user = ciolaRepo.findById(currentUserId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + currentUserId));

        // Get or create media
        Media media = mediaRepo.findByTmdbIdAndMediaType(mediaId, MediaType.valueOf(mediaType.toUpperCase()))
                .orElseGet(() -> createMedia(mediaId, MediaType.valueOf(mediaType.toUpperCase())));

        // Upsert using optimized single query
        Instant now = Instant.now();
        ContinueWatching continueWatching = ContinueWatching.builder()
                .user(user)
                .media(media)
                .currentTime(request.getTimestamp())
                .createdAt(now)
                .updatedAt(now)
                .build();
        poolService.addContinueWatching(continueWatching);
    }

    private Media createMedia(String tmdbId, MediaType mediaType) {
        Media media = Media.builder()
                .tmdbId(tmdbId)
                .mediaType(mediaType)
                .build();
        return mediaRepo.save(media);
    }
}
