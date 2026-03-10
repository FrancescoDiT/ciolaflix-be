package xyz.fdt.ciolaflixbe.service;

import it.trinex.blackout.security.BlackoutUserPrincipal;
import it.trinex.blackout.service.CurrentUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.fdt.ciolaflixbe.dto.request.ContinueWatchingRequestDTO;
import xyz.fdt.ciolaflixbe.dto.request.MediaRequestDTO;
import xyz.fdt.ciolaflixbe.dto.response.ContinueWatchingResponse;
import xyz.fdt.ciolaflixbe.exception.continuewatching.ContinueWatchingNotFoundException;
import xyz.fdt.ciolaflixbe.exception.continuewatching.MovieCannotHaveSeasonsOrEpisodesException;
import xyz.fdt.ciolaflixbe.exception.media.MediaNotFoundException;
import xyz.fdt.ciolaflixbe.exception.user.UserNotFoundException;
import xyz.fdt.ciolaflixbe.mapper.ContinueWatchingMapper;
import xyz.fdt.ciolaflixbe.model.CiolaMan;
import xyz.fdt.ciolaflixbe.model.Media;
import xyz.fdt.ciolaflixbe.model.MediaType;
import xyz.fdt.ciolaflixbe.model.continueWatching.ContinueWatching;
import xyz.fdt.ciolaflixbe.repo.CiolaRepo;
import xyz.fdt.ciolaflixbe.repo.ContinueWatchingRepo;
import xyz.fdt.ciolaflixbe.repo.MediaRepo;
import xyz.fdt.ciolaflixbe.utils.WebClientUtil;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContinueWatchingService {

    private final ContinueWatchingRepo continueWatchingRepo;
    private final CiolaRepo ciolaRepo;
    private final MediaRepo mediaRepo;
    private final CurrentUserService<BlackoutUserPrincipal> currentUserService;
    private final ContinueWatchingMapper continueWatchingMapper;
    private final WebClientUtil webClientUtil;

    /**
     * Adds or updates a continue watching entry.
     */
    @Transactional
    public void addContinueWatching(ContinueWatchingRequestDTO request) {
        String mediaId = request.getMediaId();
        String mediaType = request.getMediaType();

        if (MediaType.MOVIE == MediaType.fromString(request.getMediaType())){
           if (request.getSeasonId() != null || request.getEpisodeId() != null) {
               throw new MovieCannotHaveSeasonsOrEpisodesException("Movie media type cannot have seasons or episodes");
           }
            webClientUtil.checkIfMediaExistsOnTMDB(mediaId, MediaType.fromString(mediaType));
        }

        int seasonId = request.getSeasonId() != null ? request.getSeasonId() : 0;
        int episodeId = request.getEpisodeId() != null ? request.getEpisodeId() : 0;

        if(MediaType.TV == MediaType.fromString(request.getMediaType())){
            webClientUtil.checkIfMediaExistsOnTMDB(mediaId,
                seasonId,
                episodeId);
        }

        Long currentUserId = currentUserService.getCurrentPrincipal().getUserId();
        CiolaMan user = ciolaRepo.findById(currentUserId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + currentUserId));

        Media media = mediaRepo.findByTmdbIdAndMediaType(mediaId, MediaType.fromString(mediaType))
                .orElseGet(() -> createMedia(mediaId, MediaType.fromString(mediaType)));

        ContinueWatching continueWatching = continueWatchingRepo.findByCiolaManIdAndMediaId(user.getId(), media.getId())
                .orElse(new ContinueWatching(user, media,
                    seasonId,
                    episodeId));

        continueWatching.setCurrentTime(request.getCurrentTime());

        continueWatchingRepo.save(continueWatching);
    }

    @Transactional
    public void deleteContinueWatching(MediaRequestDTO request) {
        String mediaId = request.getMediaId();
        String mediaType = request.getMediaType();

        webClientUtil.checkIfMediaExistsOnTMDB(mediaId, MediaType.fromString(mediaType));

        Long currentUserId = currentUserService.getCurrentPrincipal().getUserId();
        CiolaMan user = ciolaRepo.findById(currentUserId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + currentUserId));

        Media media = mediaRepo.findByTmdbIdAndMediaType(mediaId, MediaType.fromString(mediaType))
                .orElseThrow(() -> new MediaNotFoundException("Media not found with id: " + mediaId + " and type: " + mediaType.toUpperCase()));

        if (!continueWatchingRepo.existsByCiolaManIdAndMediaId(user.getId(), media.getId())) {
            throw new ContinueWatchingNotFoundException("Continue watching not found for user and media");
        }

        continueWatchingRepo.deleteByCiolaManIdAndMediaId(user.getId(), media.getId());
    }

    public List<ContinueWatchingResponse> getContinueWatching() {
        Long currentUserId = currentUserService.getCurrentPrincipal().getUserId();

        List<ContinueWatching> continueWatchingList = continueWatchingRepo.findByCiolaManIdOrderByUpdatedAtDesc(currentUserId);

        return continueWatchingMapper.toContinueWatchingResponseList(continueWatchingList);
    }

    private Media createMedia(String tmdbId, MediaType mediaType) {
        Media media = Media.builder()
                .tmdbId(tmdbId)
                .mediaType(mediaType)
                .build();
        return mediaRepo.save(media);
    }
}
