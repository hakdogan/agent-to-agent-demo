package org.jugistanbul.a2a;

import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import jakarta.inject.Inject;
import org.jugistanbul.a2a.host.AgentMessenger;

import java.util.Scanner;

/**
 * @author hakdogan (hakdogan75@gmail.com)
 * Created on 27.09.2025
 ***/
@QuarkusMain
public class Main implements QuarkusApplication {

    @Inject
    private AgentMessenger messenger;

    @Override
    public int run(String... args) throws Exception {

        System.out.printf("Ask to learn the price of any currency, stock, or precious metal you want or to get investment advice.%nType 'exit' to exit!%n");

        try (Scanner scanner = new Scanner(System.in)) {
            String request;
            while (!(request = scanner.nextLine()).equalsIgnoreCase("exit")) {
                messenger.deliver(request);
                System.out.printf("%nask new question%n");
            }
        }

        System.out.println("Good bye!");
        return 0;
    }
}
