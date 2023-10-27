package enseirb.concurrence.restful;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Queue;


@Component
public class Consumer {

    private final ArrayList<String> lastMessages = new ArrayList<String>(10);

    @KafkaListener(id = "3mousquetaires", topics = "msg.txt")
    public void receive(String msg){
        if(lastMessages.size() >= 10)
            lastMessages.remove(0);
        lastMessages.add(msg);
    }

    public ArrayList<String> returnMessages(){
        return lastMessages;
    }
}
