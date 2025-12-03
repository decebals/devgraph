package ro.suiu.devgraph.falkordb.repository;

import org.springframework.data.falkordb.repository.FalkorDBRepository;
import org.springframework.stereotype.Repository;
import ro.suiu.devgraph.falkordb.domain.Developer;

@Repository
public interface DeveloperRepository extends FalkorDBRepository<Developer, String> {

    boolean existsByEmail(String email);

}