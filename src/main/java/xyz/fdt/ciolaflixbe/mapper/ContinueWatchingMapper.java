package xyz.fdt.ciolaflixbe.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import xyz.fdt.ciolaflixbe.dto.response.ContinueWatchingResponse;
import xyz.fdt.ciolaflixbe.model.continueWatching.ContinueWatching;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ContinueWatchingMapper {

    @Mapping(target = "mediaId", source = "media.tmdbId")
    @Mapping(target = "mediaType", source = "media.mediaType")
    ContinueWatchingResponse toContinueWatchingResponse(ContinueWatching continueWatching);

    List<ContinueWatchingResponse> toContinueWatchingResponseList(List<ContinueWatching> continueWatching);
}
