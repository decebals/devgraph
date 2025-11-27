package ro.suiu.devgraph.dto;

public record CreateSkillRequest(
        String name,
        String category,
        String description
) {
}
