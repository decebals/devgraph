package ro.suiu.devgraph.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ro.suiu.devgraph.dto.DeveloperDetailsResponse;
import ro.suiu.devgraph.dto.DeveloperResponse;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for {@link DeveloperController} using Neo4j as the graph database.
 * Extends {@link Neo4jBaseControllerIntegrationTest} to inherit Neo4j container setup and utilities.
 *
 * <p>These tests verify the full integration stack:</p>
 * <ul>
 *   <li>HTTP REST endpoints</li>
 *   <li>Controller layer</li>
 *   <li>Service layer (Neo4jDeveloperService)</li>
 *   <li>Repository layer (Spring Data Neo4j)</li>
 *   <li>Actual Neo4j database (via Testcontainers)</li>
 * </ul>
 *
 * <p>The database is automatically cleared before each test to ensure isolation.</p>
 */
class DeveloperControllerIntegrationTest extends Neo4jBaseControllerIntegrationTest {

    private static final String DEVELOPERS_ENDPOINT = "/api/v1/developers";

    /**
     * Tests GET /api/v1/developers endpoint.
     * Verifies that the endpoint returns HTTP 200 OK and a non-null response body.
     */
    @Test
    void getAllDevelopers() {
        // given - when
        ResponseEntity<DeveloperResponse[]> response = restTemplate.getForEntity(DEVELOPERS_ENDPOINT, DeveloperResponse[].class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    /**
     * Tests GET /api/v1/developers/{id} endpoint.
     * Creates a test developer with skills in the database and verifies
     * that the endpoint returns the developer with all related data.
     */
    @Test
    void getDeveloperById() {
        // given
        String testDeveloperId = createTestDeveloperWithSkills();

        // when
        ResponseEntity<DeveloperDetailsResponse> response = restTemplate.getForEntity(DEVELOPERS_ENDPOINT + "/" + testDeveloperId, DeveloperDetailsResponse.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().name()).isEqualTo("Test Developer");
        assertThat(response.getBody().skills()).hasSize(2);
    }

    /**
     * Creates a test developer with two skills in the Neo4j database.
     * Uses Cypher to create the developer node and relationships to skills.
     *
     * @return the UUID of the created developer
     */
    private String createTestDeveloperWithSkills() {
        String cypher = """
                    CREATE (d:Developer {id: randomUUID(), name: 'Test Developer', email: 'test@example.com'})
                    CREATE (s1:Skill {name: 'Java'})
                    CREATE (s2:Skill {name: 'Neo4j'})
                    CREATE (d)-[:KNOWS]->(s1)
                    CREATE (d)-[:KNOWS]->(s2)
                    RETURN d.id as developerId
                """;

        return neo4jClient.query(cypher)
                .fetchAs(String.class)
                .one()
                .orElseThrow();
    }

}