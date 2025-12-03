package ro.suiu.devgraph.neo4j.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.suiu.devgraph.neo4j.domain.Project;
import ro.suiu.devgraph.dto.CreateProjectRequest;
import ro.suiu.devgraph.dto.ProjectResponse;
import ro.suiu.devgraph.dto.UpdateProjectRequest;
import ro.suiu.devgraph.exception.NotFoundException;
import ro.suiu.devgraph.mapper.ProjectMapper;
import ro.suiu.devgraph.neo4j.repository.ProjectRepository;
import ro.suiu.devgraph.service.ProjectService;

import java.util.List;

@Service
@Profile("neo4j")
public class Neo4jProjectService implements ProjectService {

    private static final Logger log = LoggerFactory.getLogger(Neo4jProjectService.class);

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    public Neo4jProjectService(ProjectRepository projectRepository, ProjectMapper projectMapper) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
    }

    /**
     * Get all projects.
     */
    public List<ProjectResponse> getAllProjects() {
        log.debug("Fetching all projects");
        List<Project> projects = projectRepository.findAll();
        return projectMapper.toResponseList(projects);
    }

    /**
     * Get project by ID.
     */
    public ProjectResponse getProjectById(String id) {
        log.debug("Fetching project with id: {}", id);
        Project project = findProjectOrThrow(id);
        return projectMapper.toResponse(project);
    }

    /**
     * Create new project.
     */
    @Transactional
    public ProjectResponse createProject(CreateProjectRequest request) {
        log.debug("Creating new project: {}", request.name());

        Project project = projectMapper.toEntity(request);
        Project saved = projectRepository.save(project);

        log.info("Created project with id: {}", saved.getId());
        return projectMapper.toResponse(saved);
    }

    /**
     * Update existing project.
     */
    @Transactional
    public ProjectResponse updateProject(String id, UpdateProjectRequest request) {
        log.debug("Updating project with id: {}", id);

        Project project = findProjectOrThrow(id);

        // Update only non-null fields
        projectMapper.updateEntity(request, project);
        Project updated = projectRepository.save(project);

        log.info("Updated project with id: {}", id);
        return projectMapper.toResponse(updated);
    }

    /**
     * Delete project.
     */
    @Transactional
    public void deleteProject(String id) {
        log.debug("Deleting project with id: {}", id);

        if (!projectRepository.existsById(id)) {
            throw new NotFoundException("Project not found with id: " + id);
        }

        projectRepository.deleteById(id);
        log.info("Deleted project with id: {}", id);
    }

    /**
     * Helper method to find project or throw exception.
     */
    private Project findProjectOrThrow(String id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Project not found with id: " + id));
    }

}
