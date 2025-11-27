package ro.suiu.devgraph.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ro.suiu.devgraph.domain.Project;
import ro.suiu.devgraph.dto.CreateProjectRequest;
import ro.suiu.devgraph.dto.ProjectResponse;
import ro.suiu.devgraph.dto.UpdateProjectRequest;

import java.util.List;

/**
 * Mapper for {@link Project} entity and related DTOs.
 */
@Mapper(componentModel = "spring")
public interface ProjectMapper {

    // READS (Entity → Response)

    ProjectResponse toResponse(Project entity);

    List<ProjectResponse> toResponseList(List<Project> entities);

    // WRITES (Request → Entity)

    Project toEntity(CreateProjectRequest request);

    @Mapping(target = "id", ignore = true)
    void updateEntity(UpdateProjectRequest request, @MappingTarget Project entity);

}
