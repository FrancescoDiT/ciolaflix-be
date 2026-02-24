package xyz.fdt.ciolaflixbe.model.continueWatching;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.*;
import xyz.fdt.ciolaflixbe.model.CiolaMan;
import xyz.fdt.ciolaflixbe.model.Media;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "continue_watching", uniqueConstraints = {
    @UniqueConstraint(name = "uk_continue_watching_user_media", columnNames = {"user_id", "media_id"})
})
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContinueWatching {

    @EmbeddedId
    private ContinueWatchingId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id", nullable = false)
    private CiolaMan user;

    @ManyToOne
    @MapsId("mediaId")
    @JoinColumn(name = "media_id", nullable = false)
    private Media media;

    @Column(name = "playback_time", nullable = false)
    private String currentTime;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @Builder
    public ContinueWatching(CiolaMan user, Media media, String currentTime) {
        this.user = user;
        this.media = media;
        this.currentTime = currentTime;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = Instant.now();
        if (this.id == null) {
            this.id = new ContinueWatchingId(
                this.user != null ? this.user.getId() : null,
                this.media != null ? this.media.getId() : null
            );
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = Instant.now();
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
