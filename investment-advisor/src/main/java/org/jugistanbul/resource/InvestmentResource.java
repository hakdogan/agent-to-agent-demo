package org.jugistanbul.resource;

import io.smallrye.mutiny.Multi;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.jugistanbul.ai.agent.api.InvestmentAssistant;

/**
 * @author hakdogan (hakdogan75@gmail.com)
 * Created on 27.09.2025
 ***/

@Path("/api/v1")
public class InvestmentResource {

    private final InvestmentAssistant advisor;

    public InvestmentResource(InvestmentAssistant advisor) {
        this.advisor = advisor;
    }

    @GET
    @Path("/investment/{question}")
    @Produces(MediaType.TEXT_PLAIN)
    public Multi<String> info(String question){
        return advisor.priceInfo(question);
    }
}
