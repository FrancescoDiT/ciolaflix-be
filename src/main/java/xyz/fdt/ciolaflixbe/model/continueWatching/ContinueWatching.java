package xyz.fdt.ciolaflixbe.model.continueWatching;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import xyz.fdt.ciolaflixbe.model.CiolaMan;
import xyz.fdt.ciolaflixbe.model.Media;

import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "continue_watching", uniqueConstraints = {
    @UniqueConstraint(name = "uk_continue_watching_user_media", columnNames = {"ciola_man_id", "media_id"})
})
@Getter
@Setter
@NoArgsConstructor
public class ContinueWatching {

    @EmbeddedId
    private ContinueWatchingId id = new ContinueWatchingId();

    @ManyToOne
    @MapsId("ciolaManId")
    @JoinColumn(name = "ciola_man_id")
    private CiolaMan ciolaMan;

    @ManyToOne
    @MapsId("mediaId")
    @JoinColumn(name = "media_id")
    private Media media;

    @Column(name = "playback_time", nullable = false)
    private Integer currentTime;

    @Column(name = "season_id", nullable = true)
    private Integer seasonId;

    @Column(name = "episode_id", nullable = true)
    private Integer episodeId;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;

    public ContinueWatching(CiolaMan ciolaMan, Media media, int seasonId, int episodeId) {
        this.ciolaMan = ciolaMan;
        this.media = media;
        this.seasonId = seasonId;
        this.episodeId = episodeId;
        this.id = new ContinueWatchingId(ciolaMan != null ? ciolaMan.getId() : null, media != null ? media.getId() : null);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContinueWatching that = (ContinueWatching) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
