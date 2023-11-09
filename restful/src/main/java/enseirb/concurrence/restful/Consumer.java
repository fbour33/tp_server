package enseirb.concurrence.restful;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.apache.kafka.common.TopicPartition;
//import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.listener.ConsumerSeekAware;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
public class Consumer implements ConsumerSeekAware {

    @Autowired TruckService truckService;

    @Override
    public void onPartitionsAssigned(Map<TopicPartition, Long> assignments, ConsumerSeekCallback callback){
        callback.seekToTimestamp(assignments.keySet(),System.currentTimeMillis() - 3600000);
    }

    @KafkaListener(id = "coucouille", topics = "trucks.position")
    public void receive(Truck truck, Acknowledgment ack){
        truckService.addTruck(truck);
        ack.acknowledge();
    }
}
