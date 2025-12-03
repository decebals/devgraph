package ro.suiu.devgraph.neo4j.domain;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.data.neo4j.core.schema.Relationship.Direction.OUTGOING;

@Node
public class Developer {

    @Id
    @GeneratedValue(generatorClass = UUIDStringGenerator.class)
    private String id;

    private String code;
    private String name;
    private String email;
    private Integer yearsOfExperience;
    private String currentRole;

    @Relationship(type = "KNOWS", direction = OUTGOING)
    private Set<KnowsRelationship> knowsRelationships;

    @Relationship(type = "WORKED_ON", direction = OUTGOING)
    private Set<WorkedOnRelationship> workedOnRelationships;

    public String getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(Integer yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    public String getCurrentRole() {
        return currentRole;
    }

    public void setCurrentRole(String currentRole) {
        this.currentRole = currentRole;
    }

    public Set<KnowsRelationship> getKnowsRelationships() {
        return knowsRelationships;
    }

    public void addSkill(Skill skill, SkillLevel skillLevel, int yearsOfExperience) {
        KnowsRelationship knowsRelationship = new KnowsRelationship();
        knowsRelationship.setSkill(skill);
        knowsRelationship.setLevel(skillLevel);
        knowsRelationship.setYearsOfExperience(yearsOfExperience);

        if (this.knowsRelationships == null) {
            this.knowsRelationships = new HashSet<>();
        }

        this.knowsRelationships.add(knowsRelationship);
    }

    public Set<WorkedOnRelationship> getWorkedOnRelationships() {
        return workedOnRelationships;
    }

    public void addProject(Project project, String role, int durationInMonths) {
        WorkedOnRelationship workedOnRelationship = new WorkedOnRelationship();
        workedOnRelationship.setProject(project);
        workedOnRelationship.setRole(role);
        workedOnRelationship.setDurationInMonths(durationInMonths);

        if (this.workedOnRelationships == null) {
            this.workedOnRelationships = new HashSet<>();
        }

        this.workedOnRelationships.add(workedOnRelationship);
    }

}
