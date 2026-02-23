package xyz.fdt.ciolaflixbe.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import xyz.fdt.ciolaflixbe.model.liked.Liked;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LikedMapper {

    List<String> toMediaIdList(Iterable<Liked> liked);

    default String toMediaId(Liked liked) {
        if (liked == null || liked.getMedia() == null) {
            return null;
        }
        return liked.getMedia().getTmdbId();
    }
}