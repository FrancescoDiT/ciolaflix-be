package xyz.fdt.ciolaflixbe.model.watchLater;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;
import xyz.fdt.ciolaflixbe.model.CiolaMan;
import xyz.fdt.ciolaflixbe.model.Media;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "watch_later", uniqueConstraints = {
    @UniqueConstraint(name = "uk_watch_later_user_media", columnNames = {"user_id", "media_id"})
})
@Getter
@Setter
public class WatchLater {

    @EmbeddedId
    private WatchLaterId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "ciola_man", nullable = false)
    private CiolaMan ciolaMan;

    @ManyToOne
    @MapsId("mediaId")
    @JoinColumn(name = "media_id", nullable = false)
    private Media media;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public WatchLater() {
    }

    public WatchLater(CiolaMan user, Media media) {
        this.ciolaMan = user;
        this.media = media;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (this.id == null) {
            this.id = new WatchLaterId(
                this.ciolaMan != null ? this.ciolaMan.getId() : null,
                this.media != null ? this.media.getId() : null
            );
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WatchLater watchLater = (WatchLater) o;
        return Objects.equals(id, watchLater.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
