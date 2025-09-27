package org.jugistanbul.a2a.tool;

import dev.langchain4j.agent.tool.Tool;
import jakarta.enterprise.context.ApplicationScoped;
import org.jugistanbul.a2a.client.AgentClient;
import org.jugistanbul.a2a.pojo.Input;

/**
 * @author hakdogan (hakdogan75@gmail.com)
 * Created on 27.09.2025
 ***/
@ApplicationScoped
public class MarketPrice {

    @Tool("""
          It returns exchange rates, stocks, and precious metal prices from a financial expert. 
          It is used only for questions about foreign exchange rates, stocks, and precious metals prices.
          """)
    public void getMarketPrice(String question){
        AgentClient.deliverRequest(new Input("http://localhost:10001", question));
    }
}
