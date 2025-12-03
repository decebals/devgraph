package ro.suiu.devgraph.falkordb.mapper;

import org.mapstruct.AnnotateWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.context.annotation.Profile;
import ro.suiu.devgraph.falkordb.domain.Project;
import ro.suiu.devgraph.dto.CreateProjectRequest;
import ro.suiu.devgraph.dto.ProjectResponse;
import ro.suiu.devgraph.dto.UpdateProjectRequest;

import java.util.List;

/**
 * Mapper for FalkorDB Project entities and related DTOs.
 * Active only when "falkordb" profile is enabled.
 */
@Mapper(componentModel = "spring")
@AnnotateWith(value = Profile.class, elements = @AnnotateWith.Element(strings = "falkordb"))
public interface ProjectMapper {

    ProjectResponse toResponse(Project entity);

    List<ProjectResponse> toResponseList(List<Project> entities);

    Project toEntity(CreateProjectRequest request);

    @Mapping(target = "id", ignore = true)
    void updateEntity(UpdateProjectRequest request, @MappingTarget Project entity);

}