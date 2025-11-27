package ro.suiu.devgraph.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.suiu.devgraph.domain.Skill;
import ro.suiu.devgraph.dto.CreateSkillRequest;
import ro.suiu.devgraph.dto.SkillResponse;
import ro.suiu.devgraph.dto.UpdateSkillRequest;
import ro.suiu.devgraph.exception.NotFoundException;
import ro.suiu.devgraph.mapper.SkillMapper;
import ro.suiu.devgraph.repository.SkillRepository;

import java.util.List;

@Service
public class SkillService {

    private static final Logger log = LoggerFactory.getLogger(SkillService.class);

    private final SkillRepository skillRepository;
    private final SkillMapper skillMapper;

    public SkillService(SkillRepository skillRepository, SkillMapper skillMapper) {
        this.skillRepository = skillRepository;
        this.skillMapper = skillMapper;
    }

    /**
     * Get all skills.
     */
    public List<SkillResponse> getAllSkills() {
        log.debug("Fetching all skills");
        List<Skill> skills = skillRepository.findAll();
        return skillMapper.toResponseList(skills);
    }

    /**
     * Get skill by ID.
     */
    public SkillResponse getSkillById(String id) {
        log.debug("Fetching skill with id: {}", id);
        Skill skill = findSkillOrThrow(id);
        return skillMapper.toResponse(skill);
    }

    /**
     * Create new skill.
     */
    @Transactional
    public SkillResponse createSkill(CreateSkillRequest request) {
        log.debug("Creating new skill: {}", request.name());

        Skill skill = skillMapper.toEntity(request);
        Skill saved = skillRepository.save(skill);

        log.info("Created skill with id: {}", saved.getId());
        return skillMapper.toResponse(saved);
    }

    /**
     * Update existing skill.
     */
    @Transactional
    public SkillResponse updateSkill(String id, UpdateSkillRequest request) {
        log.debug("Updating skill with id: {}", id);

        Skill skill = findSkillOrThrow(id);

        // Update only non-null fields
        skillMapper.updateEntity(request, skill);
        Skill updated = skillRepository.save(skill);

        log.info("Updated skill with id: {}", id);
        return skillMapper.toResponse(updated);
    }

    /**
     * Delete skill.
     */
    @Transactional
    public void deleteSkill(String id) {
        log.debug("Deleting skill with id: {}", id);

        if (!skillRepository.existsById(id)) {
            throw new NotFoundException("Skill not found with id: " + id);
        }

        skillRepository.deleteById(id);
        log.info("Deleted skill with id: {}", id);
    }

    /**
     * Helper method to find skill or throw exception.
     */
    private Skill findSkillOrThrow(String id) {
        return skillRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Skill not found with id: " + id));
    }

}
