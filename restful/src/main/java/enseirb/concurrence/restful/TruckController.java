package enseirb.concurrence.restful;

import org.springframework.beans.factory.annotation.Autowired;
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
    public Truck getTruckById(@PathVariable long id){
        return truckRepo.getTruckById(id);
    }
}
