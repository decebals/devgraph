package ro.suiu.devgraph.falkordb.util;

import org.springframework.data.falkordb.core.schema.IdGenerator;

import java.util.UUID;

/**
 * UUID-based String ID generator for FalkorDB entities.
 */
public class UUIDStringGenerator implements IdGenerator<String> {

    @Override
    public String generateId(String primaryLabel, Object entity) {
        return UUID.randomUUID().toString();
    }

}
