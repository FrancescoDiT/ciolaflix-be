package xyz.fdt.ciolaflixbe.model.watchLater;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import xyz.fdt.ciolaflixbe.model.CiolaMan;
import xyz.fdt.ciolaflixbe.model.Media;

import java.time.Instant;

@Entity
@Table(name = "watch_later")
@Getter
@Setter
@NoArgsConstructor
public class WatchLater {

    @EmbeddedId
    private WatchLaterId id = new WatchLaterId();

    @ManyToOne
    @MapsId("ciolaManId")
    @JoinColumn(name = "ciola_man_id")
    private CiolaMan ciolaMan;

    @ManyToOne
    @MapsId("mediaId")
    @JoinColumn(name = "media_id")
    private Media media;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    public WatchLater(CiolaMan ciolaMan, Media media) {
        this.ciolaMan = ciolaMan;
        this.media = media;
        this.id = new WatchLaterId(ciolaMan != null ? ciolaMan.getId() : null, media != null ? media.getId() : null);
    }

}
