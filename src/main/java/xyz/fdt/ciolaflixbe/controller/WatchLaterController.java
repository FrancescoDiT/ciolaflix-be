package xyz.fdt.ciolaflixbe.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.fdt.ciolaflixbe.service.WatchLaterService;

@RestController
@RequestMapping("/watchlater")
@RequiredArgsConstructor
public class WatchLaterController {
    private final WatchLaterService watchLaterService;

    @PostMapping("/add/{mediaId}")
    public ResponseEntity<Void> addWatchLater(@PathVariable String mediaId){
        watchLaterService.addWatchLater(mediaId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{mediaId}")
    public ResponseEntity<Void> deleteWatchLater(@PathVariable String mediaId){
        watchLaterService.deleteWatchLater(mediaId);
        return ResponseEntity.ok().build();
    }
}
