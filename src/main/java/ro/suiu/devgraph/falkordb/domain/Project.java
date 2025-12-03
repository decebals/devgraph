package ro.suiu.devgraph.falkordb.domain;

import org.springframework.data.falkordb.core.schema.GeneratedValue;
import org.springframework.data.falkordb.core.schema.Id;
import org.springframework.data.falkordb.core.schema.Node;
import ro.suiu.devgraph.falkordb.util.UUIDStringGenerator;

import java.time.LocalDate;

/**
 * FalkorDB Project entity.
 * Uses UUID-based string IDs generated automatically for new entities.
 */
@Node
public class Project {

    @Id
    @GeneratedValue(generatorClass = UUIDStringGenerator.class)
    private String id;

    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}