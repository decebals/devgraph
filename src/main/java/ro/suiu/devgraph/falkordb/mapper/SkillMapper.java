package ro.suiu.devgraph.falkordb.mapper;

import org.mapstruct.AnnotateWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.context.annotation.Profile;
import ro.suiu.devgraph.falkordb.domain.Skill;
import ro.suiu.devgraph.dto.CreateSkillRequest;
import ro.suiu.devgraph.dto.SkillResponse;
import ro.suiu.devgraph.dto.UpdateSkillRequest;

import java.util.List;

/**
 * Mapper for FalkorDB Skill entities and related DTOs.
 * Active only when "falkordb" profile is enabled.
 */
@Mapper(componentModel = "spring")
@AnnotateWith(value = Profile.class, elements = @AnnotateWith.Element(strings = "falkordb"))
public interface SkillMapper {

    SkillResponse toResponse(Skill entity);

    List<SkillResponse> toResponseList(List<Skill> entities);

    Skill toEntity(CreateSkillRequest request);

    @Mapping(target = "id", ignore = true)
    void updateEntity(UpdateSkillRequest request, @MappingTarget Skill entity);

}