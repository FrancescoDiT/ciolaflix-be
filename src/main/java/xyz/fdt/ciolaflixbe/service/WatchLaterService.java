package xyz.fdt.ciolaflixbe.service;

import it.trinex.blackout.security.BlackoutUserPrincipal;
import it.trinex.blackout.service.CurrentUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.fdt.ciolaflixbe.dto.request.MediaRequestDTO;
import xyz.fdt.ciolaflixbe.dto.response.MediaAndTypeDTO;
import xyz.fdt.ciolaflixbe.exception.media.MediaNotFoundException;
import xyz.fdt.ciolaflixbe.exception.user.UserNotFoundException;
import xyz.fdt.ciolaflixbe.exception.watchlater.MediaAlreadyInWatchLaterException;
import xyz.fdt.ciolaflixbe.exception.watchlater.MediaNotInWatchLaterException;
import xyz.fdt.ciolaflixbe.mapper.WatchLaterMapper;
import xyz.fdt.ciolaflixbe.model.CiolaMan;
import xyz.fdt.ciolaflixbe.model.Media;
import xyz.fdt.ciolaflixbe.model.MediaType;
import xyz.fdt.ciolaflixbe.model.watchLater.WatchLater;
import xyz.fdt.ciolaflixbe.repo.CiolaRepo;
import xyz.fdt.ciolaflixbe.repo.MediaRepo;
import xyz.fdt.ciolaflixbe.repo.WatchLaterRepo;
import xyz.fdt.ciolaflixbe.utils.WebClientUtil;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WatchLaterService {
    private final WatchLaterRepo watchLaterRepo;
    private final CiolaRepo ciolaRepo;
    private final MediaRepo mediaRepo;
    private final CurrentUserService<BlackoutUserPrincipal> currentUserService;
    private final WebClientUtil webClientUtil;
    private final WatchLaterMapper watchLaterMapper;

    @Transactional
    public void addWatchLater(MediaRequestDTO request) {
        String mediaId = request.getMediaId();
        String mediaType = request.getMediaType();

        webClientUtil.checkIfMediaExistsOnTMDB(mediaId, MediaType.fromString(mediaType));

        Long currentUserId = currentUserService.getCurrentPrincipal().getUserId();

        CiolaMan user = ciolaRepo.findById(currentUserId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + currentUserId));

        Media media = mediaRepo.findByTmdbIdAndMediaType(mediaId, MediaType.fromString(mediaType))
                .orElseGet(() -> createMedia(mediaId, MediaType.fromString(mediaType)));

        if (watchLaterRepo.existsByCiolaManIdAndMediaId(user.getId(), media.getId())) {
            throw new MediaAlreadyInWatchLaterException("Media already in watch later list");
        }

        WatchLater watchLater = new WatchLater(user, media);
        watchLaterRepo.save(watchLater);
    }

    @Transactional
    public void deleteWatchLater(MediaRequestDTO request) {
        String mediaId = request.getMediaId();
        String mediaType = request.getMediaType();

        webClientUtil.checkIfMediaExistsOnTMDB(mediaId, MediaType.fromString(mediaType));

        Long currentUserId = currentUserService.getCurrentPrincipal().getUserId();

        CiolaMan user = ciolaRepo.findById(currentUserId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + currentUserId));

        Media media = mediaRepo.findByTmdbIdAndMediaType(mediaId, MediaType.fromString(mediaType))
                .orElseThrow(() -> new MediaNotFoundException("Media not found with id: " + mediaId + " and type: " + mediaType.toUpperCase()));

        if (!watchLaterRepo.existsByCiolaManIdAndMediaId(user.getId(), media.getId())) {
            throw new MediaNotInWatchLaterException("Media not in watch later list");
        }

        watchLaterRepo.deleteByCiolaManIdAndMediaId(user.getId(), media.getId());
    }

    public List<MediaAndTypeDTO> getWatchLater() {
        Long currentUserId = currentUserService.getCurrentPrincipal().getUserId();

        List<WatchLater> watchLaterList = watchLaterRepo.findByCiolaManId(currentUserId);

        return watchLaterMapper.toMediaAndTypeDTOList(watchLaterList);
    }

    private Media createMedia(String tmdbId, MediaType mediaType) {
        Media media = Media.builder()
                .tmdbId(tmdbId)
                .mediaType(mediaType)
                .build();
        return mediaRepo.save(media);
    }
}
