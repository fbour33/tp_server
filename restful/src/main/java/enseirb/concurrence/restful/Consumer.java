package enseirb.concurrence.restful;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.apache.kafka.common.TopicPartition;
//import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.listener.ConsumerSeekAware;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
public class Consumer implements ConsumerSeekAware {

    private final Logger log = LoggerFactory.getLogger(Consumer.class);

    @Autowired TruckService truckService;
    @Autowired AlertService alertService;

    @Override
    public void onPartitionsAssigned(Map<TopicPartition, Long> assignments, ConsumerSeekCallback callback){
        callback.seekToTimestamp(assignments.keySet(),System.currentTimeMillis() - 10*1000);
    }

    @KafkaListener(id = "3mousquetaires", topics = "trucks.position")
    public void receive(Truck truck, Acknowledgment ack){
        //log.info("Truck received" + truck.getTruckId());
        truckService.addTruck(truck);
        ack.acknowledge();
        alertService.checkPositionAndPublish(truck);
    }
}
