package enseirb.concurrence.restful;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;


@Component
public class Consumer {

    private final ArrayList<MessageFormat> lastMessages = new ArrayList<MessageFormat>(10);

    @KafkaListener(id = "3mousquetaires", topics = "mousquetaires.json")
    public void receive(MessageFormat msg){
        if(lastMessages.size() >= 10)
            lastMessages.remove(0);
        lastMessages.add(msg);
    }

    public ArrayList<MessageFormat> returnMessages(){
        return lastMessages;
    }
}
