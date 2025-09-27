# a2a-client (Host Agent)

This is the host agent that classifies user input, makes a routing decision, and forwards the request to the relevant agent using LLM function call facilities.

## Prerequisites
* JDK 17+
* Maven 3.8+
* Ollama

## To run

```bash
mvn clean install

java -jar target/quarkus-app/quarkus-run.jar
```