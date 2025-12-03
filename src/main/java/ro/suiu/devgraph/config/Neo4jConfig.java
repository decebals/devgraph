package ro.suiu.devgraph.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

/**
 * Neo4j Configuration.
 * Enables Neo4j repositories when the 'neo4j' profile is active.
 */
@Configuration
@Profile("neo4j")
@EnableNeo4jRepositories(basePackages = "ro.suiu.devgraph.neo4j.repository")
public class Neo4jConfig {
}