package enseirb.concurrence.restful;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@ComponentScan("enseirb.concurrence.restful")
public class WebClientConfig {
    public static final String MAP_URL = "http://breisen.datamix.ovh:8080";

    @Bean
    public WebClient webClient() {
        return WebClient.builder().baseUrl(MAP_URL).build();
    }
}
