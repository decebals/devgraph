package ro.suiu.devgraph.dto;

public record UpdateSkillRequest(
        String name,
        String category,
        String description
) {
}
