package ro.suiu.devgraph.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;
import ro.suiu.devgraph.domain.Skill;

import java.util.List;

@Repository
public interface SkillRepository extends Neo4jRepository<Skill, String> {

    @Query("MATCH (d:Developer {id: $developerId})-[:KNOWS]->(s:Skill) RETURN s")
    List<Skill> findByDeveloperId(String developerId);

}
