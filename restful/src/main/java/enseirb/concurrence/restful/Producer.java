package enseirb.concurrence.restful;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class Producer {

    private final Logger log = LoggerFactory.getLogger(Producer.class);

    @Autowired
    KafkaTemplate<String, Truck> kafkaTemplate;

    public void sendAlert(Truck truck){
        log.info("truck in alert : " + truck.getTruckId());
        kafkaTemplate.send("trucks.alert.mousquetaire", truck);
    }

}
