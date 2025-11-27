package ro.suiu.devgraph.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ro.suiu.devgraph.domain.Skill;
import ro.suiu.devgraph.dto.CreateSkillRequest;
import ro.suiu.devgraph.dto.SkillResponse;
import ro.suiu.devgraph.dto.UpdateSkillRequest;

import java.util.List;

/**
 * Mapper for {@link Skill} entity and related DTOs.
 */
@Mapper(componentModel = "spring")
public interface SkillMapper {

    // READS (Entity → Response)

    SkillResponse toResponse(Skill entity);

    List<SkillResponse> toResponseList(List<Skill> entities);

    // WRITES (Request → Entity)

    Skill toEntity(CreateSkillRequest request);

    @Mapping(target = "id", ignore = true)
    void updateEntity(UpdateSkillRequest request, @MappingTarget Skill entity);

}
