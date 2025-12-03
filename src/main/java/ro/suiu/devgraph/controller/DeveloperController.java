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
import ro.suiu.devgraph.dto.CreateDeveloperRequest;
import ro.suiu.devgraph.dto.DeveloperDetailsResponse;
import ro.suiu.devgraph.dto.DeveloperResponse;
import ro.suiu.devgraph.dto.UpdateDeveloperRequest;
import ro.suiu.devgraph.service.DeveloperService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/developers")
public class DeveloperController {

    private static final Logger log = LoggerFactory.getLogger(DeveloperController.class);

    private final DeveloperService developerService;

    public DeveloperController(DeveloperService developerService) {
        this.developerService = developerService;
    }

    /**
     * GET /api/v1/developers
     * Get all developers (simple list).
     */
    @GetMapping
    public ResponseEntity<List<DeveloperResponse>> getAllDevelopers() {
        log.debug("GET /api/v1/developers");
        List<DeveloperResponse> developers = developerService.getAllDevelopers();
        return ResponseEntity.ok(developers);
    }

    /**
     * GET /api/v1/developers/{id}
     * Get developer by ID with full details (skills and projects).
     */
    @GetMapping("/{id}")
    public ResponseEntity<DeveloperDetailsResponse> getDeveloperById(@PathVariable String id) {
        log.debug("GET /api/v1/developers/{}", id);
        DeveloperDetailsResponse developer = developerService.getDeveloperDetails(id);
        return ResponseEntity.ok(developer);
    }

    /**
     * POST /api/v1/developers
     * Create a new developer.
     */
    @PostMapping
    public ResponseEntity<DeveloperResponse> createDeveloper(@RequestBody CreateDeveloperRequest request) {
        log.debug("POST /api/v1/developers - name: {}", request.name());
        DeveloperResponse created = developerService.createDeveloper(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * PUT /api/v1/developers/{id}
     * Update an existing developer.
     */
    @PutMapping("/{id}")
    public ResponseEntity<DeveloperResponse> updateDeveloper(@PathVariable String id, @RequestBody UpdateDeveloperRequest request) {
        log.debug("PUT /api/v1/developers/{}", id);
        DeveloperResponse updated = developerService.updateDeveloper(id, request);
        return ResponseEntity.ok(updated);
    }

    /**
     * DELETE /api/v1/developers/{id}
     * Delete a developer.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDeveloper(@PathVariable String id) {
        log.debug("DELETE /api/v1/developers/{}", id);
        developerService.deleteDeveloper(id);
        return ResponseEntity.ok().build();
    }

}
