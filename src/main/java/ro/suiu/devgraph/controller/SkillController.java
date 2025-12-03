package ro.suiu.devgraph.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.suiu.devgraph.dto.CreateSkillRequest;
import ro.suiu.devgraph.dto.SkillResponse;
import ro.suiu.devgraph.dto.UpdateSkillRequest;
import ro.suiu.devgraph.neo4j.service.SkillService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/skills")
public class SkillController {

    private static final Logger log = LoggerFactory.getLogger(SkillController.class);

    private final SkillService skillService;

    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    /**
     * GET /api/v1/skills
     * Get all skills (simple list).
     */
    @GetMapping
    public ResponseEntity<List<SkillResponse>> getAllSkills() {
        log.debug("GET /api/v1/skills");
        List<SkillResponse> skills = skillService.getAllSkills();
        return ResponseEntity.ok(skills);
    }

    /**
     * GET /api/v1/skills/{id}
     * Get skill by ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SkillResponse> getSkillById(@PathVariable String id) {
        log.debug("GET /api/v1/skills/{}", id);
        SkillResponse skill = skillService.getSkillById(id);
        return ResponseEntity.ok(skill);
    }

    /**
     * POST /api/v1/skills
     * Create a new skill.
     */
    @PostMapping
    public ResponseEntity<SkillResponse> createSkill(@RequestBody CreateSkillRequest request) {
        log.debug("POST /api/v1/skills - name: {}", request.name());
        SkillResponse created = skillService.createSkill(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * PUT /api/v1/skills/{id}
     * Update an existing skill.
     */
    @PutMapping("/{id}")
    public ResponseEntity<SkillResponse> updateSkill(@PathVariable String id, @RequestBody UpdateSkillRequest request) {
        log.debug("PUT /api/v1/skills/{}", id);
        SkillResponse updated = skillService.updateSkill(id, request);
        return ResponseEntity.ok(updated);
    }

    /**
     * DELETE /api/v1/skills/{id}
     * Delete a skill.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSkill(@PathVariable String id) {
        log.debug("DELETE /api/v1/skills/{}", id);
        skillService.deleteSkill(id);
        return ResponseEntity.ok().build();
    }

}
