package ro.suiu.devgraph.controller;

import com.falkordb.Driver;
import com.falkordb.FalkorDB;
import com.falkordb.Graph;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * Abstract base class for controller integration tests using FalkorDB as the graph database.
 * Extends {@link BaseControllerIntegrationTest} and adds FalkorDB-specific setup.
 *
 * <p><strong>Features:</strong></p>
 * <ul>
 *   <li>FalkorDB Testcontainer with latest version</li>
 *   <li>Redis-based graph database (FalkorDB runs on Redis)</li>
 *   <li>Dynamic property configuration for Spring Boot</li>
 *   <li>JFalkorDB driver for database operations</li>
 *   <li>Cypher-based database cleanup between tests</li>
 *   <li>Active profiles: "test" and "falkordb"</li>
 * </ul>
 *
 * <p><strong>FalkorDB Profile:</strong></p>
 * The "falkordb" profile activates all FalkorDB service implementations
 * (FalkorDBDeveloperService, FalkorDBProjectService, FalkorDBSkillService).
 *
 * <p><strong>Usage:</strong></p>
 * <pre>
 * class DeveloperControllerFalkorDbIntegrationTest extends FalkorDbBaseControllerIntegrationTest {
 *
 *     {@literal @}Test
 *     void testGetAllDevelopers() {
 *         // Use restTemplate to make HTTP requests
 *     }
 * }
 * </pre>
 *
 * <p><strong>Container Lifecycle:</strong></p>
 * The FalkorDB container is static and shared across all tests in the same JVM,
 * improving test performance. The database is cleared before each test method
 * to ensure isolation.
 */
@ActiveProfiles({"test", "falkordb"})
@Testcontainers
public abstract class FalkorDbBaseControllerIntegrationTest extends BaseControllerIntegrationTest {

    private static final int FALKORDB_PORT = 6379;
    private static final String DATABASE_NAME = "devgraph";

    /**
     * FalkorDB Testcontainer instance.
     * Static container is shared across all test classes for performance.
     * Uses the official falkordb/falkordb Docker image.
     */
    @Container
    static GenericContainer<?> falkorDbContainer = new GenericContainer<>("falkordb/falkordb:latest")
            .withExposedPorts(FALKORDB_PORT)
            .withReuse(true);

    /**
     * Dynamically configures Spring Boot properties to connect to the FalkorDB test container.
     * Sets the Redis URI and database name for the test environment.
     */
    @DynamicPropertySource
    static void configureFalkorDb(DynamicPropertyRegistry registry) {
        registry.add("spring.data.falkordb.uri", () ->
                String.format("redis://%s:%d", falkorDbContainer.getHost(), falkorDbContainer.getMappedPort(FALKORDB_PORT)));
        registry.add("spring.data.falkordb.database", () -> DATABASE_NAME);
    }

    /**
     * Clears all nodes and relationships from the FalkorDB database.
     * Uses JFalkorDB driver to execute Cypher query: {@code MATCH (n) DETACH DELETE n}.
     * Called automatically before each test method to ensure test isolation.
     */
    @Override
    protected void clearDatabase() {
        try (Driver driver = FalkorDB.driver(falkorDbContainer.getHost(), falkorDbContainer.getMappedPort(FALKORDB_PORT))) {
            Graph graph = driver.graph(DATABASE_NAME);
            graph.query("MATCH (n) DETACH DELETE n");
        } catch (Exception e) {
            throw new RuntimeException("Failed to clear FalkorDB database", e);
        }
    }
}