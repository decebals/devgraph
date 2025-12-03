package ro.suiu.devgraph.neo4j.mapper;

import org.mapstruct.AnnotateWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.context.annotation.Profile;
import ro.suiu.devgraph.neo4j.domain.Project;
import ro.suiu.devgraph.dto.CreateProjectRequest;
import ro.suiu.devgraph.dto.ProjectResponse;
import ro.suiu.devgraph.dto.UpdateProjectRequest;

import java.util.List;

/**
 * Mapper for Neo4j Project entities and related DTOs.
 * Active only when "neo4j" profile is enabled.
 */
@Mapper(componentModel = "spring")
@AnnotateWith(value = Profile.class, elements = @AnnotateWith.Element(strings = "neo4j"))
public interface ProjectMapper {

    ProjectResponse toResponse(Project entity);

    List<ProjectResponse> toResponseList(List<Project> entities);

    Project toEntity(CreateProjectRequest request);

    @Mapping(target = "id", ignore = true)
    void updateEntity(UpdateProjectRequest request, @MappingTarget Project entity);

}