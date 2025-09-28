# Investment Advisor

This repository is an investment advisor agent application that provides investment advice on specified areas.

## A2A Support
The Investment Advisor exposes its capabilities to the outside via **Agent Card**, depending on the A2A protocol.

When the application is running, and you visit the [agent-card.json](http://localhost:10002/.well-known/agent-card.json) link, you should get an output similar to the one below.
```json
{
  "name": "Investment Advisor Agent",
  "description": "Provides investment consultancy",
  "url": "http://localhost:10002",
  "version": "1.0.0",
  "capabilities": {
    "streaming": true,
    "pushNotifications": false,
    "stateTransitionHistory": false
  },
  "defaultInputModes": [
    "text"
  ],
  "defaultOutputModes": [
    "text"
  ],
  "skills": [
    {
      "id": "investment_advisor",
      "name": "Investment Advisor",
      "description": "Provides investment consultancy on specified areas.",
      "tags": [
        "investment",
        "investment_advisor"
      ],
      "examples": [
        "Do you recommend investing in the stock market or real estate?",
        "Should I invest in gold or foreign currency?",
        "What should I pay attention to when trading options?"
      ]
    }
  ],
  "supportsAuthenticatedExtendedCard": false,
  "additionalInterfaces": [
    {
      "transport": "JSONRPC",
      "url": "http://localhost:10002"
    }
  ],
  "preferredTransport": "JSONRPC",
  "protocolVersion": "0.3.0.Beta1"
}
```

## Prerequisites
* JDK 17+
* Maven 3.8+
* Ollama or Docker/Podman (for automatic Ollama instance startup)

## To run

```bash
mvn quarkus:dev
```