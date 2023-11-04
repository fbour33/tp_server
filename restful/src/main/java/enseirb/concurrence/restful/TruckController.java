package enseirb.concurrence.restful;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;

@RestController
@RequestMapping("/truck")
public class TruckController {

    //@Autowired Producer producer;
    @Autowired TruckService truckService;

    @GetMapping("/{id}")
    public ResponseEntity<Position> getTruckById(@PathVariable int id) {
        try {
            Position position = truckService.getTruckPosition(id);
            return ResponseEntity.ok(position);
        } catch (IllegalAccessException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/map")
    public String redirectToTheLastPosition(@PathVariable int id) {
        return "Message added correctly";
    }
}