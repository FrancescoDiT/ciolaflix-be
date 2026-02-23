package xyz.fdt.ciolaflixbe.model.liked;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LikedId implements Serializable {

    @Column(name = "ciola_man_id")
    private Long ciolaManId;

    @Column(name = "media_id")
    private Long mediaId;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        LikedId likedId = (LikedId) o;
        return Objects.equals(ciolaManId, likedId.ciolaManId) && Objects.equals(mediaId, likedId.mediaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ciolaManId, mediaId);
    }
}
