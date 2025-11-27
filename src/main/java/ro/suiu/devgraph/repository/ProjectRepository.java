package ro.suiu.devgraph.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;
import ro.suiu.devgraph.domain.Project;

import java.util.List;

@Repository
public interface ProjectRepository extends Neo4jRepository<Project, String> {

    @Query("MATCH (d:Developer {id: $developerId})-[:WORKED_ON]->(p:Project) RETURN p")
    List<Project> findByDeveloperId(String developerId);

}
