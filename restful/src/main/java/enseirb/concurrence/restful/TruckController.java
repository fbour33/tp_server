package enseirb.concurrence.restful;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;

@RestController
@RequestMapping("/truck")
public class TruckController {

    //@Autowired Producer producer;
    @Autowired TruckService truckService;

    @GetMapping("/{id}")
    public Position getTruckById(@PathVariable int id) {
        return truckService.getTruckPosition(id);
    }

    /*@PostMapping("/messages")
    public String post(@RequestBody MessageFormat msg) {

        producer.postMessage(msg);
        return "Message added correctly";
    }*/
}