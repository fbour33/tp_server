package enseirb.concurrence.restful;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class MapFromPositionService {

    @Autowired WebClient truckClient;

    public String getUrlMap(Position position) {
        return truckClient.post().uri("/map")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(position)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
