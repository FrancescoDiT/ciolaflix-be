package xyz.fdt.ciolaflixbe.service;

import it.trinex.blackout.security.BlackoutUserPrincipal;
import it.trinex.blackout.service.CurrentUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.fdt.ciolaflixbe.dto.LikedResponse;
import xyz.fdt.ciolaflixbe.dto.request.MediaRequest;
import xyz.fdt.ciolaflixbe.exception.liked.MediaAlreadyLikedException;
import xyz.fdt.ciolaflixbe.exception.liked.MediaNotLikedException;
import xyz.fdt.ciolaflixbe.exception.media.MediaNotFoundException;
import xyz.fdt.ciolaflixbe.exception.user.UserNotFoundException;
import xyz.fdt.ciolaflixbe.mapper.LikedMapper;
import xyz.fdt.ciolaflixbe.model.CiolaMan;
import xyz.fdt.ciolaflixbe.model.MediaType;
import xyz.fdt.ciolaflixbe.model.liked.Liked;
import xyz.fdt.ciolaflixbe.model.Media;
import xyz.fdt.ciolaflixbe.repo.CiolaRepo;
import xyz.fdt.ciolaflixbe.repo.LikedRepo;
import xyz.fdt.ciolaflixbe.repo.MediaRepo;
import xyz.fdt.ciolaflixbe.utils.WebClientUtil;

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
    private final WebClientUtil webClientUtil;

    /**
     * Adds liked media; validates existence and uniqueness
     */
    @Transactional
    public void addLiked(MediaRequest request) {
        String mediaId = request.getMediaId();
        String mediaType = request.getMediaType();

        webClientUtil.checkMediaExists(mediaId, MediaType.valueOf(mediaType.toUpperCase()));

        Long currentUserId = currentUserService.getCurrentPrincipal().getUserId();

        CiolaMan user = ciolaRepo.findById(currentUserId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + currentUserId));

        Media media = mediaRepo.findByTmdbIdAndMediaType(mediaId, MediaType.valueOf(mediaType.toUpperCase()))
                .orElseGet(() -> createMedia(mediaId, MediaType.valueOf(mediaType.toUpperCase())));

        if (likedRepo.existsByCiolaManIdAndMediaId(user.getId(), media.getId())) {
            throw new MediaAlreadyLikedException();
        }

        Liked liked = new Liked(user, media);
        likedRepo.save(liked);
    }

    public void deleteLiked(MediaRequest request) {
        String mediaId = request.getMediaId();
        String mediaType = request.getMediaType();

        webClientUtil.checkMediaExists(mediaId, MediaType.valueOf(mediaType.toUpperCase()));

        Long currentUserId = currentUserService.getCurrentPrincipal().getUserId();

        CiolaMan user = ciolaRepo.findById(currentUserId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + currentUserId));

        Media media = mediaRepo.findByTmdbIdAndMediaType(mediaId, MediaType.valueOf(mediaType.toUpperCase()))
                .orElseThrow(() -> new MediaNotFoundException("Media not found with id: " + mediaId + " and type: " + mediaType.toUpperCase()));

        if (!likedRepo.existsByCiolaManIdAndMediaId(user.getId(), media.getId())) {
            throw new MediaNotLikedException("Media not liked by user");
        }

        likedRepo.deleteByCiolaManIdAndMediaId(user.getId(), media.getId());
    }

    public LikedResponse getLikedMediaIds() {
        Long currentUserId = currentUserService.getCurrentPrincipal().getUserId();

        List<Liked> likedList = likedRepo.findByCiolaManId(currentUserId);
        List<String> mediaIds = likedMapper.toMediaIdList(likedList);

        return LikedResponse.builder()
                .mediaIds(mediaIds)
                .build();
    }

    private Media createMedia(String tmdbId, MediaType mediaType) {
        Media media = Media.builder()
                .tmdbId(tmdbId)
                .mediaType(mediaType)
                .build();
        return mediaRepo.save(media);
    }
}
