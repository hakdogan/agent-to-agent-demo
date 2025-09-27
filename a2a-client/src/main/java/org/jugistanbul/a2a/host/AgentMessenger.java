package org.jugistanbul.a2a.host;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;
import io.quarkiverse.langchain4j.ToolBox;
import jakarta.enterprise.context.ApplicationScoped;
import org.jugistanbul.a2a.tool.MarketPrice;
import org.jugistanbul.a2a.tool.InvestmentAdvisor;

/**
 * @author hakdogan (hakdogan75@gmail.com)
 * Created on 27.09.2025
 ***/
@ApplicationScoped
@RegisterAiService

@SystemMessage("""
               Analyze the user request and categorize it as 'market_price' or 'investment_advice'.
               In case the request doesn't belong to any of those categories categorize it as 'unknown'.
               Reply questions categorized as 'market_prices' with the MarketPrice tool, 
               questions categorized as 'investment_advice' with the InvestmentAdvice tool, 
               and return 'Please rephrase your question more clearly for better understanding' for undefined questions.         
               """)
public interface AgentMessenger {

    @UserMessage("The user request is: '{request}'.")
    @ToolBox({MarketPrice.class, InvestmentAdvisor.class})
    String deliver(String request);
}
