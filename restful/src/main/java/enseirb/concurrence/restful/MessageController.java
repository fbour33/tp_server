package enseirb.concurrence.restful;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class MessageController {

    @Autowired Producer producer;
    @Autowired Consumer consumer;

    @GetMapping("/messages")
    public ArrayList<String> getMessages() {
        return consumer.returnMessages();
    }

    @PostMapping("/messages")
    public String post(@RequestBody String msg) {
        producer.postMessage(msg);
        return "Message added correctly";
    }
}