package ro.suiu.devgraph.falkordb.migration;

import com.falkordb.Driver;
import com.falkordb.FalkorDB;
import com.falkordb.Graph;
import com.falkordb.Record;
import com.falkordb.ResultSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Minimalist migration system for FalkorDB.
 * Executes Cypher files and tracks applied versions.
 */
@Component
@Profile("falkordb")
public class FalkorDBMigrations implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(FalkorDBMigrations.class);
    private static final Pattern VERSION_PATTERN = Pattern.compile("V(\\d+)__(.+)\\.cypher");

    @Value("${spring.data.falkordb.uri:redis://localhost:6379}")
    private String uri;

    @Value("${spring.data.falkordb.database:devgraph}")
    private String database;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("Starting FalkorDB migrations");

        try (Driver driver = FalkorDB.driver(extractHost(uri), extractPort(uri))) {
            Graph graph = driver.graph(database);

            Set<String> applied = getAppliedVersions(graph);
            List<Migration> pending = scanMigrations(applied);

            if (pending.isEmpty()) {
                log.info("No pending migrations");
                return;
            }

            log.info("Applying {} migration(s)", pending.size());
            for (Migration m : pending) {
                applyMigration(graph, m);
            }
        }

        log.info("FalkorDB migrations completed");
    }

    private Set<String> getAppliedVersions(Graph graph) {
        Set<String> versions = new HashSet<>();
        try {
            ResultSet rs = graph.query("MATCH (m:__FalkorDBMigration) RETURN m.version AS version");
            for (Record record : rs) {
                versions.add(record.getValue("version").toString());
            }
        } catch (Exception e) {
            log.debug("Migration tracking not initialized yet");
        }
        return versions;
    }

    private List<Migration> scanMigrations(Set<String> applied) throws Exception {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources("classpath:falkordb/migrations/*.cypher");

        List<Migration> pending = new ArrayList<>();
        for (Resource r : resources) {
            String name = r.getFilename();
            if (name == null) continue;

            Matcher m = VERSION_PATTERN.matcher(name);
            if (m.matches()) {
                String version = m.group(1);
                if (!applied.contains(version)) {
                    String desc = m.group(2).replace('_', ' ');
                    String content = r.getContentAsString(StandardCharsets.UTF_8);
                    pending.add(new Migration(version, desc, content));
                }
            }
        }

        pending.sort(Comparator.comparing(Migration::version));
        return pending;
    }

    private void applyMigration(Graph graph, Migration m) {
        log.info("Applying V{}: {}", m.version(), m.description());

        // Execute migration statements
        for (String stmt : m.content().split(";")) {
            String trimmed = stmt.trim();
            if (!trimmed.isEmpty() && !trimmed.startsWith("//")) {
                log.debug("Executing migration V{} statement: {}", m.version(), trimmed);
                try {
                    graph.query(trimmed);
                } catch (Exception e) {
                    log.error("Failed executing migration V{} statement: {}\nMigration: V{} - {}\nError: {}",
                            m.version(), trimmed, m.version(), m.description(), e.getMessage(), e);
                    throw e;
                }
            }
        }

        // Record migration
        String recordQuery = String.format(
                "CREATE (:__FalkorDBMigration {version: '%s', description: '%s', appliedAt: '%s', appliedBy: '%s'})",
                m.version(),
                m.description().replace("'", "\\'"),
                Instant.now().toString(),
                System.getProperty("user.name", "devgraph")
        );
        graph.query(recordQuery);

        log.info("Applied V{}", m.version());
    }

    private String extractHost(String uri) {
        return uri.replaceAll("redis://([^:]+).*", "$1");
    }

    private int extractPort(String uri) {
        if (uri.contains(":") && uri.lastIndexOf(":") > 6) {
            return Integer.parseInt(uri.substring(uri.lastIndexOf(":") + 1));
        }
        return 6379;
    }

    private record Migration(String version, String description, String content) {}

}