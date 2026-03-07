package xyz.fdt.ciolaflixbe.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import xyz.fdt.ciolaflixbe.dto.response.MediaAndTypeDTO;
import xyz.fdt.ciolaflixbe.model.continueWatching.ContinueWatching;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ContinueWatchingMapper {

    @Mapping(target = "mediaId", source = "media.tmdbId")
    @Mapping(target = "mediaType", source = "media.mediaType")
    MediaAndTypeDTO toContinueWatchingMediaDTO(ContinueWatching continueWatching);

    List<MediaAndTypeDTO> toContinueWatchingMediaDTOList(List<ContinueWatching> continueWatching);
}
