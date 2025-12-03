package ro.suiu.devgraph.neo4j.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;
import ro.suiu.devgraph.neo4j.domain.Developer;

@Repository
public interface DeveloperRepository extends Neo4jRepository<Developer, String> {

    boolean existsByEmail(String email);

}
