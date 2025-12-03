package ro.suiu.devgraph.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.Neo4jContainer;
import org.testcontainers.junit.jupiter.Container;

/**
 * Abstract base class for controller integration tests using Neo4j as the graph database.
 * Extends {@link BaseControllerIntegrationTest} and adds Neo4j-specific setup.
 *
 * <p><strong>Features:</strong></p>
 * <ul>
 *   <li>Neo4j Testcontainer with version 5.15 Community Edition</li>
 *   <li>Automatic service connection to Spring Boot application</li>
 *   <li>Neo4jClient for advanced queries and database operations</li>
 *   <li>Cypher-based database cleanup between tests</li>
 *   <li>Active profiles: "test" and "neo4j"</li>
 * </ul>
 *
 * <p><strong>Neo4j Profile:</strong></p>
 * The "neo4j" profile activates all Neo4j service implementations
 * (Neo4jDeveloperService, Neo4jProjectService, Neo4jSkillService).
 *
 * <p><strong>Usage:</strong></p>
 * <pre>
 * class DeveloperControllerIntegrationTest extends Neo4jBaseControllerIntegrationTest {
 *
 *     {@literal @}Test
 *     void testGetAllDevelopers() {
 *         // Use restTemplate to make HTTP requests
 *         // Use neo4jClient for database setup/verification
 *     }
 * }
 * </pre>
 *
 * <p><strong>Container Lifecycle:</strong></p>
 * The Neo4j container is static and shared across all tests in the same JVM,
 * improving test performance. The database is cleared before each test method
 * to ensure isolation.
 */
@ActiveProfiles({"test", "neo4j"})
public abstract class Neo4jBaseControllerIntegrationTest extends BaseControllerIntegrationTest {

    /**
     * Neo4j Testcontainer instance.
     * Static container is shared across all test classes for performance.
     * Automatically connected to Spring Boot application via {@link ServiceConnection}.
     */
    @Container
    @ServiceConnection
    static Neo4jContainer<?> neo4jContainer = new Neo4jContainer<>("neo4j:5.15-community");

    /**
     * Neo4j client for executing Cypher queries.
     * Useful for:
     * <ul>
     *   <li>Setting up test data with complex relationships</li>
     *   <li>Verifying database state after operations</li>
     *   <li>Advanced queries not covered by repositories</li>
     * </ul>
     */
    @Autowired
    protected Neo4jClient neo4jClient;

    /**
     * Clears all nodes and relationships from the Neo4j database.
     * Executes a Cypher query to delete all data: {@code MATCH (n) DETACH DELETE n}.
     * Called automatically before each test method to ensure test isolation.
     */
    @Override
    protected void clearDatabase() {
        neo4jClient.query("MATCH (n) DETACH DELETE n").run();
    }
}