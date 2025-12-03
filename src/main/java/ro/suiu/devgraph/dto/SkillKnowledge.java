package ro.suiu.devgraph.dto;

import ro.suiu.devgraph.neo4j.domain.SkillLevel;

public record SkillKnowledge(
        String skillId,
        SkillLevel level,
        Integer yearsOfExperience
) {
}
