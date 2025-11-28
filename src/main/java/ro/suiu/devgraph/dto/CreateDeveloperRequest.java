package ro.suiu.devgraph.dto;

import java.util.List;

public record CreateDeveloperRequest(
        String code,
        String name,
        String email,
        Integer yearsOfExperience,
        String currentRole,
        List<SkillKnowledge> skills,
        List<ProjectWorkedOn> projects
) {
}
