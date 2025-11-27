package ro.suiu.devgraph.dto;

public record CreateDeveloperRequest(
        String code,
        String name,
        String email,
        Integer yearsOfExperience,
        String currentRole
) {
}
