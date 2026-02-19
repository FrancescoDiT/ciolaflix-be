package xyz.fdt.ciolaflixbe.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CiolaMan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long authAccountId;

    @ManyToMany
    @JoinTable(
            name = "ciola_man_liked",
            joinColumns = @JoinColumn(name = "ciola_man_id"),
            inverseJoinColumns = @JoinColumn(name = "liked_id")
    )
    private Set<Liked> liked = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "ciola_man_watchlater",
        joinColumns = @JoinColumn(name = "ciola_man_id"),
        inverseJoinColumns = @JoinColumn(name = "watchlater_id")
    )
    private Set<WatchLater> watchLater = new HashSet<>();

    @OneToMany(mappedBy = "ciolaMan")
    private Set<ContinueWatching> continueWatchings = new HashSet<>();

    private Instant updatedAt;

    @PreUpdate
    private void onUpdate() {
        this.updatedAt = Instant.now();
    }
}
