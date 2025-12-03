package ro.suiu.devgraph.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ro.suiu.devgraph.neo4j.domain.Developer;
import ro.suiu.devgraph.dto.CreateDeveloperRequest;
import ro.suiu.devgraph.dto.DeveloperDetailsResponse;
import ro.suiu.devgraph.dto.DeveloperResponse;
import ro.suiu.devgraph.dto.UpdateDeveloperRequest;

import java.util.List;

/**
 * Mapper for {@link Developer} entity and related DTOs.
 */
@Mapper(componentModel = "spring")
public interface DeveloperMapper {

    // READS (Entity → Response)

    DeveloperResponse toResponse(Developer entity);

    @Mapping(target = "skills", ignore = true)
    @Mapping(target = "projects", ignore = true)
    DeveloperDetailsResponse toDetailsResponse(Developer entity);

    List<DeveloperResponse> toResponseList(List<Developer> entities);

    // WRITES (Request → Entity)

    Developer toEntity(CreateDeveloperRequest request);

    @Mapping(target = "id", ignore = true)
    void updateEntity(UpdateDeveloperRequest request, @MappingTarget Developer entity);

}