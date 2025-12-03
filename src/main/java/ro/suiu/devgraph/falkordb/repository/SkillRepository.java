package ro.suiu.devgraph.falkordb.repository;

import org.springframework.data.falkordb.repository.FalkorDBRepository;
import org.springframework.data.falkordb.repository.query.Query;
import org.springframework.stereotype.Repository;
import ro.suiu.devgraph.falkordb.domain.Skill;

import java.util.List;

@Repository
public interface SkillRepository extends FalkorDBRepository<Skill, String> {

    @Query("MATCH (d:Developer {id: $developerId})-[:KNOWS]->(s:Skill) RETURN s")
    List<Skill> findByDeveloperId(String developerId);

}