package org.jugistanbul.ai.agent.api;

import dev.langchain4j.service.SystemMessage;
import io.quarkiverse.langchain4j.RegisterAiService;
import io.smallrye.mutiny.Multi;
import jakarta.enterprise.context.ApplicationScoped;


/**
 * @author hakdogan (hakdogan75@gmail.com)
 * Created on 27.09.2025
 ***/
@RegisterAiService
@ApplicationScoped
@SystemMessage("""
                You are an investment advisor. Provide investment advice in the areas asked. 
                In your responses, state that your recommendations are suggestions 
                and that you are not responsible for any financial consequences.
               """)

public interface InvestmentAssistant {
    Multi<String> priceInfo(String question);
}