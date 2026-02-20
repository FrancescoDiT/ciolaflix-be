package xyz.fdt.ciolaflixbe.mapper;

import org.mapstruct.Mapper;
import xyz.fdt.ciolaflixbe.model.Liked;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LikedMapper {

    default List<String> toMediaIdList(List<Liked> liked) {
        return liked.stream()
                .filter(l -> l != null && l.getMedia() != null)
                .map(l -> l.getMedia().getTmdbId())
                .toList();
    }
}
