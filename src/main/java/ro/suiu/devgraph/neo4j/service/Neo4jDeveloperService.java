package ro.suiu.devgraph.neo4j.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.suiu.devgraph.neo4j.domain.Developer;
import ro.suiu.devgraph.neo4j.domain.KnowsRelationship;
import ro.suiu.devgraph.neo4j.domain.Project;
import ro.suiu.devgraph.neo4j.domain.Skill;
import ro.suiu.devgraph.neo4j.domain.WorkedOnRelationship;
import ro.suiu.devgraph.dto.CreateDeveloperRequest;
import ro.suiu.devgraph.dto.DeveloperDetailsResponse;
import ro.suiu.devgraph.dto.DeveloperResponse;
import ro.suiu.devgraph.dto.ProjectResponse;
import ro.suiu.devgraph.dto.SkillResponse;
import ro.suiu.devgraph.dto.UpdateDeveloperRequest;
import ro.suiu.devgraph.exception.NotFoundException;
import ro.suiu.devgraph.mapper.DeveloperMapper;
import ro.suiu.devgraph.mapper.ProjectMapper;
import ro.suiu.devgraph.mapper.SkillMapper;
import ro.suiu.devgraph.neo4j.repository.DeveloperRepository;
import ro.suiu.devgraph.neo4j.repository.ProjectRepository;
import ro.suiu.devgraph.neo4j.repository.SkillRepository;
import ro.suiu.devgraph.service.DeveloperService;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class Neo4jDeveloperService implements DeveloperService {

    private static final Logger log = LoggerFactory.getLogger(Neo4jDeveloperService.class);

    private final DeveloperRepository developerRepository;
    private final SkillRepository skillRepository;
    private final ProjectRepository projectRepository;
    private final DeveloperMapper developerMapper;
    private final SkillMapper skillMapper;
    private final ProjectMapper projectMapper;

    public Neo4jDeveloperService(DeveloperRepository developerRepository, SkillRepository skillRepository, ProjectRepository projectRepository,
                                  DeveloperMapper developerMapper, SkillMapper skillMapper, ProjectMapper projectMapper) {
        this.developerRepository = developerRepository;
        this.skillRepository = skillRepository;
        this.projectRepository = projectRepository;
        this.developerMapper = developerMapper;
        this.skillMapper = skillMapper;
        this.projectMapper = projectMapper;
    }

    /**
     * Get all developers.
     */
    public List<DeveloperResponse> getAllDevelopers() {
        log.debug("Fetching all developers");
        List<Developer> developers = developerRepository.findAll();
        return developerMapper.toResponseList(developers);
    }

    /**
     * Get developer by ID with basic info.
     */
    public DeveloperResponse getDeveloperById(String id) {
        log.debug("Fetching developer with id: {}", id);
        Developer developer = findDeveloperOrThrow(id);
        return developerMapper.toResponse(developer);
    }

    /**
     * Get developer by ID with full details (skills and projects).
     */
    public DeveloperDetailsResponse getDeveloperDetails(String id) {
        log.debug("Fetching developer details for id: {}", id);

        // Fetch developer
        Developer developer = findDeveloperOrThrow(id);

        // Fetch skills via Cypher query
        List<Skill> skills = developer.getKnowsRelationships()
                .stream()
                .map(KnowsRelationship::getSkill)
                .toList();
        List<SkillResponse> skillResponses = skillMapper.toResponseList(skills);

        // Fetch project contributions via Cypher query
        List<Project> projects = developer.getWorkedOnRelationships()
                .stream()
                .map(WorkedOnRelationship::getProject)
                .toList();
        List<ProjectResponse> projectResponses = projectMapper.toResponseList(projects);

        // Build detailed DTO
        return new DeveloperDetailsResponse(
                developer.getId(),
                developer.getCode(),
                developer.getName(),
                developer.getEmail(),
                developer.getYearsOfExperience(),
                developer.getCurrentRole(),
                skillResponses,
                projectResponses
        );
    }

    /**
     * Create new developer.
     */
    @Transactional
    public DeveloperResponse createDeveloper(CreateDeveloperRequest request) {
        log.debug("Creating new developer: {}", request.name());

        // Check if email already exists
        if (developerRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Email already exists: " + request.email());
        }

        // Convert request to entity
        Developer developer = developerMapper.toEntity(request);

        // Add skills if provided
        if (request.skills() != null) {
            request.skills().forEach(skillRequest -> {
                Skill skill = skillRepository.findById(skillRequest.skillId()).orElseThrow(() -> new NotFoundException("Skill not found with id: " + skillRequest));
                developer.addSkill(skill, skillRequest.level(), skillRequest.yearsOfExperience());
            });
        }

        // Add projects if provided
        if (request.projects() != null) {
            request.projects().forEach(projectRequest -> {
                Project project = projectRepository.findById(projectRequest.projectId()).orElseThrow(() -> new NotFoundException("Project not found with id: " + projectRequest));
                developer.addProject(project, projectRequest.role(), projectRequest.durationInMonths());
            });
        }

        // Save entity
        Developer saved = developerRepository.save(developer);

        log.info("Created developer with id: {}", saved.getId());
        return developerMapper.toResponse(saved);
    }

    /**
     * Update existing developer.
     */
    @Transactional
    public DeveloperResponse updateDeveloper(String id, UpdateDeveloperRequest request) {
        log.debug("Updating developer with id: {}", id);

        Developer developer = findDeveloperOrThrow(id);

        // Check email uniqueness if email is being updated
        if (request.email() != null && !request.email().equals(developer.getEmail())) {
            if (developerRepository.existsByEmail(request.email())) {
                throw new IllegalArgumentException("Email already exists: " + request.email());
            }
        }

        // Update only non-null fields
        developerMapper.updateEntity(request, developer);
        Developer updated = developerRepository.save(developer);

        log.info("Updated developer with id: {}", id);
        return developerMapper.toResponse(updated);
    }

    /**
     * Delete developer.
     */
    @Transactional
    public void deleteDeveloper(String id) {
        log.debug("Deleting developer with id: {}", id);

        if (!developerRepository.existsById(id)) {
            throw new NotFoundException("Developer not found with id: " + id);
        }

        developerRepository.deleteById(id);
        log.info("Deleted developer with id: {}", id);
    }

    /**
     * Helper method to find developer or throw exception.
     */
    private Developer findDeveloperOrThrow(String id) {
        return developerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Developer not found with id: " + id));
    }

}
