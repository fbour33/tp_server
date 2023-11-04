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
    @Autowired MapFromPositionService mapFromPositionService;

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
    public ResponseEntity<String> redirectToTheLastPosition(@PathVariable int id) {
        try {
            Position position = truckService.getTruckPosition(id);
            return ResponseEntity.ok(mapFromPositionService.getMapWithPosition(position));
        } catch (IllegalAccessException e){
            return ResponseEntity.notFound().build();
        }
    }
}