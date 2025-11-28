// Create sample developers
CREATE (d1:Developer {
  id: randomUUID(),
  code: 'DEV001',
  name: 'John Doe',
  email: 'john@example.com',
  currentRole: 'Senior Java Developer',
  yearsOfExperience: 8
});

CREATE (d2:Developer {
  id: randomUUID(),
  code: 'DEV002',
  name: 'Jane Smith',
  email: 'jane@example.com',
  currentRole: 'Tech Lead',
  yearsOfExperience: 10
});

// Create sample projects
CREATE (p1:Project {
  id: randomUUID(),
  code: 'PROJ001',
  name: 'E-Commerce Platform',
  description: 'Online shopping platform with microservices architecture'
});

CREATE (p2:Project {
  id: randomUUID(),
  code: 'PROJ002',
  name: 'Analytics Dashboard',
  description: 'Real-time business intelligence dashboard'
});

// Create sample skills
CREATE (s1:Skill {
  id: randomUUID(),
  name: 'Java',
  category: 'Language',
  description: 'Object-oriented programming language'
});

CREATE (s2:Skill {
  id: randomUUID(),
  name: 'Spring Boot',
  category: 'Framework',
  description: 'Java framework for building microservices'
});

CREATE (s3:Skill {
  id: randomUUID(),
  name: 'Neo4j',
  category: 'Database',
  description: 'Graph database management system'
});

CREATE (s4:Skill {
  id: randomUUID(),
  name: 'Architecture',
  category: 'Soft Skill',
  description: 'System design and architecture'
});

CREATE (s5:Skill {
  id: randomUUID(),
  name: 'Leadership',
  category: 'Soft Skill',
  description: 'Team leadership and mentoring'
});

// Create relationships - Developer KNOWS Skills
MATCH (d:Developer {code: 'DEV001'}), (s:Skill {name: 'Java'})
CREATE (d)-[:KNOWS {level: 'EXPERT', yearsOfExperience: 8}]->(s);

MATCH (d:Developer {code: 'DEV001'}), (s:Skill {name: 'Spring Boot'})
CREATE (d)-[:KNOWS {level: 'ADVANCED', yearsOfExperience: 5}]->(s);

MATCH (d:Developer {code: 'DEV001'}), (s:Skill {name: 'Neo4j'})
CREATE (d)-[:KNOWS {level: 'INTERMEDIATE', yearsOfExperience: 2}]->(s);

MATCH (d:Developer {code: 'DEV002'}), (s:Skill {name: 'Java'})
CREATE (d)-[:KNOWS {level: 'EXPERT', yearsOfExperience: 10}]->(s);

MATCH (d:Developer {code: 'DEV002'}), (s:Skill {name: 'Leadership'})
CREATE (d)-[:KNOWS {level: 'EXPERT', yearsOfExperience: 5}]->(s);

MATCH (d:Developer {code: 'DEV002'}), (s:Skill {name: 'Architecture'})
CREATE (d)-[:KNOWS {level: 'EXPERT', yearsOfExperience: 7}]->(s);

// Developer WORKED_ON Project relationships
MATCH (d:Developer {code: 'DEV001'}), (p:Project {code: 'PROJ001'})
CREATE (d)-[:WORKED_ON {role: 'Backend Developer', startDate: date('2023-01-01'), endDate: date('2024-06-30')}]->(p);

MATCH (d:Developer {code: 'DEV002'}), (p:Project {code: 'PROJ001'})
CREATE (d)-[:WORKED_ON {role: 'Tech Lead', startDate: date('2022-06-01')}]->(p);

MATCH (d:Developer {code: 'DEV002'}), (p:Project {code: 'PROJ002'})
CREATE (d)-[:WORKED_ON {role: 'Architect', startDate: date('2024-01-01')}]->(p);