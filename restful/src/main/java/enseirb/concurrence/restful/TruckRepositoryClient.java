package enseirb.concurrence.restful;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class TruckRepositoryClient {
    @Autowired
    WebClient truckClient;
    public Truck getById(long id) {
        return truckClient.get().uri("/truck/{id}", id)
                .retrieve()
                .bodyToMono(Truck.class)
                .block();
    }

}
