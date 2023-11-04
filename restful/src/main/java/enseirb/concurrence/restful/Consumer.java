package enseirb.concurrence.restful;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.ArrayList;


@Component
public class Consumer {

  @Autowired TruckService truckService;

    @KafkaListener(id = "dartagnan", topics = "trucks.position")
    public void receive(Truck truck, Acknowledgment ack){
        truckService.addTruck(truck);
        ack.acknowledge();
    }
}
