package org.jugistanbul.ai.agent.protocol;

import io.a2a.server.agentexecution.AgentExecutor;
import io.a2a.server.agentexecution.RequestContext;
import io.a2a.server.events.EventQueue;
import io.a2a.server.tasks.TaskUpdater;
import io.a2a.spec.*;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import org.jugistanbul.ai.agent.api.InvestmentAssistant;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hakdogan (hakdogan75@gmail.com)
 * Created on 27.09.2025
 ***/
@ApplicationScoped
public class InvestmentAdvisorExecutorProducer {

    @Inject
    InvestmentAssistant investmentAdvisor;

    @Produces
    public AgentExecutor agentExecutor() {
        return new InvestmentAdvisorExecutor(investmentAdvisor);
    }

    private record InvestmentAdvisorExecutor(InvestmentAssistant investmentAssistant) implements AgentExecutor {

        @Override
        public void execute(RequestContext context, EventQueue eventQueue) throws JSONRPCError {

            TaskUpdater updater = new TaskUpdater(context, eventQueue);

            if (context.getTask() == null) {
                updater.submit();
            }
            updater.startWork();

            String userMessage = extractTextFromMessage(context.getMessage());
            Uni<List<TextPart>> completionUni = investmentAssistant.priceInfo(userMessage)
                    .onItem()
                    .transform(input -> new TextPart(input, null))
                    .collect().asList();

            List<TextPart> textParts = completionUni.await().atMost(Duration.ofMinutes(1));
            List<Part<?>> parts = new ArrayList<>(textParts);

            updater.addArtifact(parts, null, null, null);
            updater.complete();
        }

        @Override
        public void cancel(RequestContext context, EventQueue eventQueue) throws JSONRPCError {
            Task task = context.getTask();

            if (task.getStatus().state() == TaskState.CANCELED) {
                throw new TaskNotCancelableError();
            }

            if (task.getStatus().state() == TaskState.COMPLETED) {
                throw new TaskNotCancelableError();
            }

            TaskUpdater updater = new TaskUpdater(context, eventQueue);
            updater.cancel();
        }

        private String extractTextFromMessage(Message message) {
            StringBuilder textBuilder = new StringBuilder();
            if (message.getParts() != null) {
                for (Part part : message.getParts()) {
                    if (part instanceof TextPart textPart) {
                        textBuilder.append(textPart.getText());
                    }
                }
            }
            return textBuilder.toString();
        }
    }
}
