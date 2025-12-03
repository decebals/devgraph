package ro.suiu.devgraph.falkordb.repository;

import org.springframework.data.falkordb.repository.FalkorDBRepository;
import org.springframework.data.falkordb.repository.query.Query;
import org.springframework.stereotype.Repository;
import ro.suiu.devgraph.falkordb.domain.Project;

import java.util.List;

@Repository
public interface ProjectRepository extends FalkorDBRepository<Project, String> {

    @Query("MATCH (d:Developer {id: $developerId})-[:WORKED_ON]->(p:Project) RETURN p")
    List<Project> findByDeveloperId(String developerId);

}