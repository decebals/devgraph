package ro.suiu.devgraph.falkordb.domain;

import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.falkordb.core.schema.Id;
import org.springframework.data.falkordb.core.schema.Node;

import java.util.UUID;

/**
 * FalkorDB Skill entity.
 * Uses UUID-based string IDs generated automatically for new entities.
 */
@Node
public class Skill {

    @Id
    private String id;

    private String name;
    private String category;
    private String description;

    public Skill() {
        this.id = UUID.randomUUID().toString();
    }

    @PersistenceCreator
    public Skill(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}