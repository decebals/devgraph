package ro.suiu.devgraph.service;

import ro.suiu.devgraph.dto.CreateSkillRequest;
import ro.suiu.devgraph.dto.SkillResponse;
import ro.suiu.devgraph.dto.UpdateSkillRequest;

import java.util.List;

/**
 * Service interface for managing skills.
 * Implementations can be backed by different graph databases (Neo4j, FalkorDB, etc.).
 */
public interface SkillService {

    /**
     * Get all skills.
     */
    List<SkillResponse> getAllSkills();

    /**
     * Get skill by ID.
     */
    SkillResponse getSkillById(String id);

    /**
     * Create new skill.
     */
    SkillResponse createSkill(CreateSkillRequest request);

    /**
     * Update existing skill.
     */
    SkillResponse updateSkill(String id, UpdateSkillRequest request);

    /**
     * Delete skill.
     */
    void deleteSkill(String id);
}