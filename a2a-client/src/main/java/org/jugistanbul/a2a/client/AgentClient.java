package org.jugistanbul.a2a.client;

import java.util.List;
import java.util.concurrent.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import io.a2a.A2A;
import io.a2a.client.*;
import io.a2a.client.config.ClientConfig;
import io.a2a.client.http.A2ACardResolver;
import io.a2a.client.transport.jsonrpc.JSONRPCTransport;
import io.a2a.client.transport.jsonrpc.JSONRPCTransportConfig;
import io.a2a.spec.*;
import org.jugistanbul.a2a.pojo.Input;

/**
 * @author hakdogan (hakdogan75@gmail.com)
 * Created on 27.09.2025
 ***/
public class AgentClient {

    public static void deliverRequest(final Input input) {
        process(input);
    }

    private static void process(Input input) {

        CompletableFuture<Void> future = new CompletableFuture<>();

        try {

            AgentCard agentCard = new A2ACardResolver(input.url()).getAgentCard();
            List<BiConsumer<ClientEvent, AgentCard>> consumers = getBiConsumers(future);

            Consumer<Throwable> errorHandler = future::completeExceptionally;

            Client client = Client
                    .builder(agentCard)
                    .clientConfig(getClientConfig())
                    .withTransport(JSONRPCTransport.class, new JSONRPCTransportConfig())
                    .addConsumers(consumers)
                    .streamingErrorHandler(errorHandler)
                    .build();

            Message message = A2A.toUserMessage(input.question());
            client.sendMessage(message, consumers, errorHandler);

            future.get();
            client.close();

        } catch (A2AClientError | A2AClientException | InterruptedException | ExecutionException e) {
            future.completeExceptionally(e);
        }
    }

    private static ClientConfig getClientConfig() {
        return new ClientConfig.Builder()
                .setAcceptedOutputModes(List.of("text"))
                .build();
    }

    private static List<BiConsumer<ClientEvent, AgentCard>> getBiConsumers(CompletableFuture<Void> future) {
        List<BiConsumer<ClientEvent, AgentCard>> consumers = List.of(
                (event, card) -> {
                    if (event instanceof MessageEvent messageEvent) {
                        appendMessageParts(messageEvent, future);
                    } else if (event instanceof TaskEvent taskEvent) {
                        if (isStatusCompleted(taskEvent.getTask().getStatus())) {
                            appendTaskArtifacts(taskEvent, future);
                        }
                    } else if (event instanceof TaskUpdateEvent taskUpdateEvent) {
                        System.out.printf("Task status: %s%n", taskUpdateEvent.getTask().getStatus().state().asString());
                        if (isStatusCompleted(taskUpdateEvent.getTask().getStatus())) {
                            appendTaskArtifacts(taskUpdateEvent, future);
                        }
                    }
                }
        );
        return consumers;
    }

    private static boolean isStatusCompleted(final TaskStatus status) {
        return switch (status.state()) {
            case COMPLETED, CANCELED, FAILED -> true;
            default -> false;
        };
    }


    private static void appendMessageParts(MessageEvent event,
                                           CompletableFuture<Void> future) {
        event.getMessage().getParts().stream()
                .filter(p -> p instanceof TextPart)
                .map(p -> ((TextPart) p).getText())
                .forEach(System.out::print);

        future.complete(null);
    }

    private static void appendTaskArtifacts(TaskEvent event,
                                            CompletableFuture<Void> future) {
        event.getTask().getArtifacts().stream()
                .flatMap(a -> a.parts().stream())
                .filter(p -> p instanceof TextPart)
                .map(p -> ((TextPart) p).getText())
                .forEach(System.out::print);

        future.complete(null);
    }

    private static void appendTaskArtifacts(TaskUpdateEvent event,
                                            CompletableFuture<Void> future) {
        event.getTask().getArtifacts().stream()
                .flatMap(a -> a.parts().stream())
                .filter(p -> p instanceof TextPart)
                .map(p -> ((TextPart) p).getText())
                .forEach(AgentClient::printMessage);

        future.complete(null);
    }

    private static void printMessage(String message) {
        try {
            TimeUnit.MILLISECONDS.sleep(75);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.print(message);
    }
}
