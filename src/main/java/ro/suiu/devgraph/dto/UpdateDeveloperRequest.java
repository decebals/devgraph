package ro.suiu.devgraph.dto;

public record UpdateDeveloperRequest(
        String code,
        String name,
        String email,
        Integer yearsOfExperience,
        String currentRole
) {
}
