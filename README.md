# Developer Skills Network

[![GitHub Actions Status](https://github.com/decebals/devgraph/actions/workflows/build.yml/badge.svg)](https://github.com/decebals/devgraph/actions/workflows/build.yml)

A graph-based system for discovering, analyzing, and connecting software developers through their skills, projects, and professional relationships.

## Project Purpose

This is a **demonstration project** showcasing:
- Graph database modeling with **Neo4j**
- **Spring Boot** REST API development
- Domain-Driven Design principles
- Incremental development approach

**Target audience**: Technical interview / Portfolio demonstration

## Quick Start

```bash
# Clone the repository
git clone https://github.com/decebals/devgraph.git
cd devgraph

# Start Neo4j (Docker)
docker-compose up -d

# Run the application
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev

# Access the API
curl http://localhost:8080/api/v1/developers
```

## Tech Stack

- **Java 25** - Programming language
- **Spring Boot 3.x** - Application framework
- **Spring Data Neo4j** - Graph database integration
- **Neo4j 5.x** - Graph database
- **Neo4j Migrations** - Database migrations
- **MapStruct** - Object mapping (DTOs)
- **Maven** - Build tool
- **Docker** - Containerization (for Neo4j)

## Learning Objectives

Through this project, you'll explore:
1. When and why to use a graph database
2. Graph modeling vs relational modeling
3. Cypher query language
4. Spring Data Neo4j repositories and custom queries
5. REST API design for graph data

## Core Concepts

```
(Developer)-[:KNOWS {level, yearsOfExperience}]->(Skill)
(Developer)-[:WORKED_ON {role, duration}]->(Project)
(Project)-[:USES]->(Skill)
(Developer)-[:WORKED_WITH]->(Developer)
```

## Use Cases

1. **Find developers by skills** - Search for specific skill combinations
2. **Discover similar developers** - Based on skill overlap and project history
3. **Recommend next skill** - Based on career progression patterns
4. **Analyze skills gaps** - For teams and projects
5. **Build optimal teams** - Based on complementary skills and collaboration history

## Development Approach

This project follows an **incremental development model**:

- [x] Core domain model + basic CRUD operations
- [x] Graph queries and relationship traversals
- [ ] Advanced queries and recommendations
- [ ] Polish, testing, and documentation

## Not covered

- Authentication/Authorization
- Frontend/UI
- Extensive testing
- Performance optimization
- Monitoring/Logging
- Deployment/Scaling

## Contributing

This is a learning/demonstration project. Feel free to fork and experiment!