package xyz.fdt.ciolaflixbe.model.continueWatching;

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
public class ContinueWatchingId implements Serializable {

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
        ContinueWatchingId that = (ContinueWatchingId) o;
        return Objects.equals(ciolaManId, that.ciolaManId) && Objects.equals(mediaId, that.mediaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ciolaManId, mediaId);
    }
}
