package xyz.fdt.ciolaflixbe.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import xyz.fdt.ciolaflixbe.model.continueWatching.ContinueWatching;
import xyz.fdt.ciolaflixbe.repo.ContinueWatchingRepo;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PoolService {

    private final ContinueWatchingRepo continueWatchingRepo;
    private Set<ContinueWatching> continueWatchingList;

    public void addContinueWatching(ContinueWatching continueWatching) {
        continueWatchingList = continueWatchingList.stream()
                .filter((c) -> !(
                        (c.getUser().equals(continueWatching.getUser())) && (c.getMedia().equals(continueWatching.getMedia()))
                    )
                )
                .collect(Collectors.toSet());
        continueWatchingList.add(continueWatching);
    }
//
//    @Scheduled(fixedRate = 180000)
//    public void flashContinueWatching() {
//        for (ContinueWatching continueWatching : continueWatchingList) {
//            continueWatchingRepo.upsertContinueWatching(
//                    continueWatching.getUser().getId(),
//                    continueWatching.getMedia().getId(),
//                    continueWatching.getCurrentTime(),
//                    Instant.now(),
//                    Instant.now()
//            );
//        }
//    }

}
