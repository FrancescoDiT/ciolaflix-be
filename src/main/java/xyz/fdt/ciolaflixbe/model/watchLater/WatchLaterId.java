package xyz.fdt.ciolaflixbe.model.watchLater;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WatchLaterId implements Serializable {

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
        WatchLaterId that = (WatchLaterId) o;
        return Objects.equals(ciolaManId, that.ciolaManId) && Objects.equals(mediaId, that.mediaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ciolaManId, mediaId);
    }
}
