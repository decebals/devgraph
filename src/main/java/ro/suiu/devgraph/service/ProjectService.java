package ro.suiu.devgraph.service;

import ro.suiu.devgraph.dto.CreateProjectRequest;
import ro.suiu.devgraph.dto.ProjectResponse;
import ro.suiu.devgraph.dto.UpdateProjectRequest;

import java.util.List;

/**
 * Service interface for managing projects.
 * Implementations can be backed by different graph databases (Neo4j, FalkorDB, etc.).
 */
public interface ProjectService {

    /**
     * Get all projects.
     */
    List<ProjectResponse> getAllProjects();

    /**
     * Get project by ID.
     */
    ProjectResponse getProjectById(String id);

    /**
     * Create new project.
     */
    ProjectResponse createProject(CreateProjectRequest request);

    /**
     * Update existing project.
     */
    ProjectResponse updateProject(String id, UpdateProjectRequest request);

    /**
     * Delete project.
     */
    void deleteProject(String id);
}