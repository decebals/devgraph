package ro.suiu.devgraph.neo4j.mapper;

import org.mapstruct.AnnotateWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.context.annotation.Profile;
import ro.suiu.devgraph.neo4j.domain.Skill;
import ro.suiu.devgraph.dto.CreateSkillRequest;
import ro.suiu.devgraph.dto.SkillResponse;
import ro.suiu.devgraph.dto.UpdateSkillRequest;

import java.util.List;

/**
 * Mapper for Neo4j Skill entities and related DTOs.
 * Active only when "neo4j" profile is enabled.
 */
@Mapper(componentModel = "spring")
@AnnotateWith(value = Profile.class, elements = @AnnotateWith.Element(strings = "neo4j"))
public interface SkillMapper {

    SkillResponse toResponse(Skill entity);

    List<SkillResponse> toResponseList(List<Skill> entities);

    Skill toEntity(CreateSkillRequest request);

    @Mapping(target = "id", ignore = true)
    void updateEntity(UpdateSkillRequest request, @MappingTarget Skill entity);

}