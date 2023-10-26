package enseirb.concurrence.restful;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequestMapping("/truck")
public class TruckController {

    @Autowired TruckRepository truckRepo;

    @GetMapping("/{id}")
    public ResponseEntity<Truck> getById(@PathVariable long id){
        try {
            Truck truck = truckRepo.getTruckById(id);
            return new ResponseEntity<>(truck, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
