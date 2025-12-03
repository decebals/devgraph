package ro.suiu.devgraph.falkordb.domain;

import org.springframework.data.falkordb.core.schema.GeneratedValue;
import org.springframework.data.falkordb.core.schema.Id;
import org.springframework.data.falkordb.core.schema.RelationshipProperties;
import org.springframework.data.falkordb.core.schema.TargetNode;
import ro.suiu.devgraph.domain.SkillLevel;

@RelationshipProperties
public class KnowsRelationship {

    @Id
    @GeneratedValue
    private Long id;

    @TargetNode
    private Skill skill;

    private SkillLevel level;
    private Integer yearsOfExperience;

    public Long getId() {
        return id;
    }

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    public SkillLevel getLevel() {
        return level;
    }

    public void setLevel(SkillLevel level) {
        this.level = level;
    }

    public Integer getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(Integer yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

}