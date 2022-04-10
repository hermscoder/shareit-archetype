# Foobar

Shareit Archetype is an archetype with the goal to unify and improve the way new microservices are built for the shareit
platform. 

## Installation

To generate the archetype you just have to run the following maven command:

```bash
mvn clean install
```

## Usage

```markdown
mvn archetype:generate \
-DarchetypeGroupId=com.shareit \
-DarchetypeArtifactId=shareit-archetype \
-DarchetypeVersion=1.0-SNAPSHOT \
-DgroupId=[PROJECT_PACKAGE] \
-DartifactId=[PROJECT_ARTIFACT] \
-Dversion=[PROJECT_VERSION]
```

