package ro.suiu.devgraph.controller;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.Neo4jContainer;
import org.testcontainers.junit.jupiter.Container;

/**
 * Base class for integration tests of controllers.
 * Sets up a Neo4j Testcontainer and provides a TestRestTemplate for making HTTP requests.
 * Cleans the database before each test.
 * Extend this class in your controller integration test classes.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public abstract class BaseControllerIntegrationTest {

    @Container
    @ServiceConnection
    static Neo4jContainer<?> neo4jContainer = new Neo4jContainer<>("neo4j:5.15-community");

    @Autowired
    protected TestRestTemplate restTemplate;

    @Autowired
    protected Neo4jClient neo4jClient; // For advanced queries if needed

    @BeforeEach
    void setupTestData() {
        clearDatabase();
    }

    protected void clearDatabase() {
        neo4jClient.query("MATCH (n) DETACH DELETE n").run();
    }

}
