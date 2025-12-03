package ro.suiu.devgraph.falkordb.mapper;

import org.mapstruct.AnnotateWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.context.annotation.Profile;
import ro.suiu.devgraph.falkordb.domain.Developer;
import ro.suiu.devgraph.dto.CreateDeveloperRequest;
import ro.suiu.devgraph.dto.DeveloperDetailsResponse;
import ro.suiu.devgraph.dto.DeveloperResponse;
import ro.suiu.devgraph.dto.UpdateDeveloperRequest;

import java.util.List;

/**
 * Mapper for FalkorDB Developer entities and related DTOs.
 * Active only when "falkordb" profile is enabled.
 */
@Mapper(componentModel = "spring")
@AnnotateWith(value = Profile.class, elements = @AnnotateWith.Element(strings = "falkordb"))
public interface DeveloperMapper {

    DeveloperResponse toResponse(Developer entity);

    @Mapping(target = "skills", ignore = true)
    @Mapping(target = "projects", ignore = true)
    DeveloperDetailsResponse toDetailsResponse(Developer entity);

    List<DeveloperResponse> toResponseList(List<Developer> entities);

    Developer toEntity(CreateDeveloperRequest request);

    @Mapping(target = "id", ignore = true)
    void updateEntity(UpdateDeveloperRequest request, @MappingTarget Developer entity);

}