package ro.suiu.devgraph.controller;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ActiveProfiles;

/**
 * Abstract base class for all controller integration tests.
 * Provides common setup and utilities for testing REST controllers across different database implementations.
 *
 * <p>This class is database-agnostic. Database-specific implementations (Neo4j, FalkorDB, etc.)
 * should extend this class and provide their own database setup, containers, and cleanup logic.</p>
 *
 * <p><strong>Features:</strong></p>
 * <ul>
 *   <li>Spring Boot test with random port for isolated testing</li>
 *   <li>TestRestTemplate for making HTTP requests to controllers</li>
 *   <li>Test profile activation</li>
 *   <li>Automatic database cleanup before each test</li>
 * </ul>
 *
 * <p><strong>Architecture:</strong></p>
 * <pre>
 * BaseControllerIntegrationTest (this class)
 *   ├── Neo4jBaseControllerIntegrationTest - Neo4j-specific setup
 *   └── FalkorDbBaseControllerIntegrationTest - FalkorDB-specific setup (future)
 *       └── DeveloperControllerIntegrationTest - actual test methods
 * </pre>
 *
 * <p><strong>Usage:</strong></p>
 * <pre>
 * {@literal @}ActiveProfiles({"test", "neo4j"})
 * public abstract class Neo4jBaseControllerIntegrationTest extends BaseControllerIntegrationTest {
 *     // Neo4j-specific container and client setup
 *     {@literal @}Override
 *     protected void clearDatabase() {
 *         // Neo4j-specific cleanup
 *     }
 * }
 * </pre>
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public abstract class BaseControllerIntegrationTest {

    /**
     * REST client for making HTTP requests to the application under test.
     * Automatically configured with the application's random port.
     */
    @Autowired
    protected TestRestTemplate restTemplate;

    /**
     * Sets up test data before each test method.
     * Ensures test isolation by clearing the database before each test.
     */
    @BeforeEach
    void setupTestData() {
        clearDatabase();
    }

    /**
     * Clears all data from the database to ensure test isolation.
     * Must be implemented by database-specific subclasses to provide
     * the appropriate cleanup mechanism for their database type.
     */
    protected abstract void clearDatabase();
}