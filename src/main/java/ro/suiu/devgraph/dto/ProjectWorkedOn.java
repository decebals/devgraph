package ro.suiu.devgraph.dto;

public record ProjectWorkedOn(
        String projectId,
        String role,
        Integer durationInMonths
) {
}
