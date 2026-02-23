package xyz.fdt.ciolaflixbe.model.liked;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;
import xyz.fdt.ciolaflixbe.model.CiolaMan;
import xyz.fdt.ciolaflixbe.model.Media;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "liked")
@Getter
@Setter
@NoArgsConstructor
public class Liked {

    @EmbeddedId
    private LikedId id = new LikedId();

    public Liked(CiolaMan ciolaMan, Media media) {
        this.ciolaMan = ciolaMan;
        this.media = media;
        this.id = new LikedId(ciolaMan != null ? ciolaMan.getId() : null, media != null ? media.getId() : null);
    }

    @ManyToOne
    @MapsId("ciolaManId")
    @JoinColumn(name = "ciola_man_id")
    private CiolaMan ciolaMan;

    @ManyToOne
    @MapsId("mediaId")
    @JoinColumn(name = "media_id")
    private Media media;

    private Instant likedAt;

    @PrePersist
    public void onPersist() {
        likedAt = Instant.now();
    }
}
