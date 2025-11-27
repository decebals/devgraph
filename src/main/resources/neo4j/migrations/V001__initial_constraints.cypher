// Create constraints for unique codes
CREATE CONSTRAINT developer_code IF NOT EXISTS
FOR (d:Developer) REQUIRE d.code IS UNIQUE;

CREATE CONSTRAINT project_code IF NOT EXISTS
FOR (p:Project) REQUIRE p.code IS UNIQUE;

CREATE CONSTRAINT technology_name IF NOT EXISTS
FOR (t:Technology) REQUIRE t.name IS UNIQUE;

// Create indexes for better performance
CREATE INDEX developer_name IF NOT EXISTS
FOR (d:Developer) ON (d.name);

CREATE INDEX project_name IF NOT EXISTS
FOR (p:Project) ON (p.name);