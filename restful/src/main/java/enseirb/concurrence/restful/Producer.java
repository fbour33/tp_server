package enseirb.concurrence.restful;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class Producer {

    public static final String TOPIC = "msg.txt";
    private final Logger log = LoggerFactory.getLogger(Producer.class);

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    public void postMessage(String msg){
        log.info("Application starting");
        kafkaTemplate.send(TOPIC, msg);
    }

}
