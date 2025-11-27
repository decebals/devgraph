package ro.suiu.devgraph.dto;

import java.util.List;

public record DeveloperDetailsResponse(
        String id,
        String code,
        String name,
        String email,
        Integer yearsOfExperience,
        String currentRole,
        List<SkillResponse> skills,    // from Skill entity
        List<ProjectResponse> projects // from Project entity
) {
}