package ro.suiu.devgraph.dto;

import java.time.LocalDate;

public record CreateProjectRequest(
        String name,
        String description,
        LocalDate startDate,
        LocalDate endDate,
        String status
) {
}
