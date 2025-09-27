package org.jugistanbul.ai.agent.protocol;

import io.a2a.server.PublicAgentCard;
import io.a2a.spec.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

import java.util.Collections;
import java.util.List;

/**
 * @author hakdogan (hakdogan75@gmail.com)
 * Created on 27.09.2025
 ***/
@ApplicationScoped
public class InvestmentAdvisorCardProducer {

    private static final String JSON_RPC_URL = "http://localhost:10002";

    @Produces
    @PublicAgentCard
    public AgentCard investmentAdvisorCardProducer() {
        return new AgentCard.Builder()
                .name("Investment Advisor Agent")
                .protocolVersion("0.3.0.Beta1")
                .description("Provides investment consultancy")
                .url(JSON_RPC_URL)
                .preferredTransport(TransportProtocol.JSONRPC.asString())
                .additionalInterfaces(
                        List.of(new AgentInterface(TransportProtocol.JSONRPC.asString(), JSON_RPC_URL))
                ).version("1.0.0")
                .capabilities(new AgentCapabilities.Builder()
                        .streaming(true)
                        .pushNotifications(false)
                        .stateTransitionHistory(false)
                        .build())
                .defaultInputModes(Collections.singletonList("text"))
                .defaultOutputModes(Collections.singletonList("text"))
                .skills(Collections.singletonList(new AgentSkill.Builder()
                        .id("investment_advisor")
                        .name("Investment Advisor")
                        .description("Provides investment consultancy on specified areas.")
                        .tags(List.of("investment", "investment_advisor"))
                        .examples(List.of("Do you recommend investing in the stock market or real estate?",
                                          "Should I invest in gold or foreign currency?",
                                          "What should I pay attention to when trading options?"))
                        .build()))
                .build();
    }
}



