package ro.suiu.devgraph.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;
import ro.suiu.devgraph.domain.Developer;

@Repository
public interface DeveloperRepository extends Neo4jRepository<Developer, String> {

    @Query("MATCH (d:Developer {email: $email}) RETURN d")
    boolean existsByEmail(String email);

}
