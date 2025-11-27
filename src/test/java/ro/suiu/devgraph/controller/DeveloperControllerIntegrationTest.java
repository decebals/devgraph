package ro.suiu.devgraph.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ro.suiu.devgraph.dto.DeveloperDetailsResponse;
import ro.suiu.devgraph.dto.DeveloperResponse;

import static org.assertj.core.api.Assertions.assertThat;

class DeveloperControllerIntegrationTest extends BaseControllerIntegrationTest {

    private static final String DEVELOPERS_ENDPOINT = "/api/v1/developers";

    @Test
    void getAllDevelopers() {
        // given - when
        ResponseEntity<DeveloperResponse[]> response = restTemplate.getForEntity(DEVELOPERS_ENDPOINT, DeveloperResponse[].class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

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

    // Helper method to create a test developer with skills in the database
    String createTestDeveloperWithSkills() {
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