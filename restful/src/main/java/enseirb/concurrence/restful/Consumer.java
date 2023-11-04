package enseirb.concurrence.restful;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.apache.kafka.common.TopicPartition;
import org.springframework.kafka.listener.ConsumerSeekAware;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
public class Consumer implements ConsumerSeekAware {

    @Autowired TruckService truckService;

    @Override
    public void onPartitionsAssigned(Map<TopicPartition, Long> assignments, ConsumerSeekCallback callback) {
        long lastHourTimestamp = System.currentTimeMillis() - 3600 * 1000;
        assignments.forEach((topicPartition, offset) -> callback.seekToTimestamp(topicPartition.topic(), topicPartition.partition(), lastHourTimestamp));
    }

    @KafkaListener(id = "dartagnan", topics = "trucks.position")
    public void receive(Truck truck, Acknowledgment ack){
        truckService.addTruck(truck);
        ack.acknowledge();
    }
}
