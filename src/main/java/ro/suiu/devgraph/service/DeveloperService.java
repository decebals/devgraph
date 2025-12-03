package ro.suiu.devgraph.service;

import ro.suiu.devgraph.dto.CreateDeveloperRequest;
import ro.suiu.devgraph.dto.DeveloperDetailsResponse;
import ro.suiu.devgraph.dto.DeveloperResponse;
import ro.suiu.devgraph.dto.UpdateDeveloperRequest;

import java.util.List;

/**
 * Service interface for managing developers.
 * Implementations can be backed by different graph databases (Neo4j, FalkorDB, etc.).
 */
public interface DeveloperService {

    /**
     * Get all developers.
     */
    List<DeveloperResponse> getAllDevelopers();

    /**
     * Get developer by ID with basic info.
     */
    DeveloperResponse getDeveloperById(String id);

    /**
     * Get developer by ID with full details (skills and projects).
     */
    DeveloperDetailsResponse getDeveloperDetails(String id);

    /**
     * Create new developer.
     */
    DeveloperResponse createDeveloper(CreateDeveloperRequest request);

    /**
     * Update existing developer.
     */
    DeveloperResponse updateDeveloper(String id, UpdateDeveloperRequest request);

    /**
     * Delete developer.
     */
    void deleteDeveloper(String id);
}