package enseirb.concurrence.restful;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;


@Component
public class AlertService {

    private final Logger log = LoggerFactory.getLogger(AlertService.class);

    @Autowired
    WebClient alertClient;
    @Autowired
    Producer alertProducer;

    @Async
    public void checkPositionAndPublish(Truck truck) {
        try {
            Integer alertNumber = alertClient.post().uri("/position/check")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(truck)
                    .retrieve()
                    .bodyToMono(Integer.class)
                    .block();
            log.info("Truck " + truck.getTruckId() + " in alert " + alertNumber.intValue());
            if (alertNumber != null && alertNumber > 0) {
                alertProducer.sendAlert(truck);
            }
        } catch (NullPointerException e) {
            log.error(e.getMessage());
        }
    }
}
