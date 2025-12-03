package ro.suiu.devgraph.dto;

import ro.suiu.devgraph.domain.SkillLevel;

public record SkillKnowledge(
        String skillId,
        SkillLevel level,
        Integer yearsOfExperience
) {
}
