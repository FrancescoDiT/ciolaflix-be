package xyz.fdt.ciolaflixbe.service;

import it.trinex.blackout.security.BlackoutUserPrincipal;
import it.trinex.blackout.service.BlackoutUserDetailService;
import it.trinex.blackout.service.CurrentUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.fdt.ciolaflixbe.model.CiolaMan;
import xyz.fdt.ciolaflixbe.repo.CiolaRepo;
import xyz.fdt.ciolaflixbe.repo.WatchLaterRepo;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WatchLaterService {
    private final WatchLaterRepo watchLaterRepo;
    private final CiolaRepo ciolaRepo;
    private final CurrentUserService<BlackoutUserPrincipal> currentUserService;


    public void addWatchLater(String mediaId){
        Long currentUserId = currentUserService.getCurrentPrincipal().getUserId();
        Optional<CiolaMan> ciolaMan = ciolaRepo.findById(currentUserId);


    }

    public void deleteWatchLater(String mediaId){
    }

}
