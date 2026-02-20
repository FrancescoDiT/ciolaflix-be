package xyz.fdt.ciolaflixbe.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
public class LikedId implements Serializable {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "media_id")
    private Long mediaId;

    public LikedId() {
    }

    public LikedId(Long userId, Long mediaId) {
        this.userId = userId;
        this.mediaId = mediaId;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LikedId likedId = (LikedId) o;
        return Objects.equals(userId, likedId.userId) && Objects.equals(mediaId, likedId.mediaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, mediaId);
    }
}
