// Create indexes for better performance
CREATE INDEX FOR (d:Developer) ON (d.email);
CREATE INDEX FOR (d:Developer) ON (d.name);
CREATE INDEX FOR (s:Skill) ON (s.name);
CREATE INDEX FOR (p:Project) ON (p.name)