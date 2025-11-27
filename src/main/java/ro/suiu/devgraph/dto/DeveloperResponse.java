package ro.suiu.devgraph.dto;

public record DeveloperResponse(
        String id,
        String code,
        String name,
        String email,
        Integer yearsOfExperience,
        String currentRole
) {
}