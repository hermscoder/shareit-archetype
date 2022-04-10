# ShareIt Archetype

ShareIt Archetype is an archetype with the goal to unify and improve the way new microservices are built for the shareit
platform. 

## Installation

To generate the archetype you just have to run the following maven command:

```bash
mvn clean install
```

## Usage
In order to use the archetype to create a new project, you should run the following maven command:

```markdown
mvn archetype:generate \
-DarchetypeGroupId=com.shareit \
-DarchetypeArtifactId=shareit-archetype \
-DarchetypeVersion=1.0-SNAPSHOT \
-DgroupId=[PROJECT_PACKAGE] \
-DartifactId=[PROJECT_ARTIFACT] \
-Dversion=[PROJECT_VERSION]
```

