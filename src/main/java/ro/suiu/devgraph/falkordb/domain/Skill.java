package ro.suiu.devgraph.falkordb.domain;

import org.springframework.data.falkordb.core.schema.GeneratedValue;
import org.springframework.data.falkordb.core.schema.Id;
import org.springframework.data.falkordb.core.schema.Node;
import ro.suiu.devgraph.falkordb.util.UUIDStringGenerator;

/**
 * FalkorDB Skill entity.
 * Uses UUID-based string IDs generated automatically for new entities.
 */
@Node
public class Skill {

    @Id
    @GeneratedValue(generatorClass = UUIDStringGenerator.class)
    private String id;

    private String name;
    private String category;
    private String description;

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