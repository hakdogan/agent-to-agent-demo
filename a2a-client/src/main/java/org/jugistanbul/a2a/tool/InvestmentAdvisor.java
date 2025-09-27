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
public class InvestmentAdvisor {

    @Tool("""
          It returns advice from an investment expert.
          It is used only for questions seeking investment advice.
          """)
    public void getInvestmentAdvice(String question){
        AgentClient.deliverRequest(new Input("http://localhost:10002", question));
    }
}
