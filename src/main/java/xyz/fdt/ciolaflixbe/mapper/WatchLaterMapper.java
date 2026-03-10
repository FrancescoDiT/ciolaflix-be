package xyz.fdt.ciolaflixbe.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import xyz.fdt.ciolaflixbe.dto.response.MediaAndTypeDTO;
import xyz.fdt.ciolaflixbe.model.watchLater.WatchLater;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WatchLaterMapper {

    @Mapping(target = "mediaId", source = "media.tmdbId")
    @Mapping(target = "mediaType", source = "media.mediaType")
    MediaAndTypeDTO toMediaAndTypeDTO(WatchLater watchLater);

    List<MediaAndTypeDTO> toMediaAndTypeDTOList(List<WatchLater> watchLater);
}
