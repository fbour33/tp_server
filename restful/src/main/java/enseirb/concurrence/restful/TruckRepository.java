package enseirb.concurrence.restful;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Component
public class TruckRepository {

    private Map<Long, Truck> truckList = new HashMap<>();

    public TruckRepository(){
        addTruck(0L, new Truck("A15 332", Instant.now(), 3987));
    }

    public void addTruck(Long id, Truck truck){
        truckList.put(id, truck);
    }

    public void removeTruck(Long id, Truck truck){
        truckList.remove(id, truck);
    }

    public Truck getTruckById(Long id) throws IllegalArgumentException {
        Truck truck = truckList.get(id);
        if(truck == null){
            throw new IllegalArgumentException("Truck does not exist");
        }
        return truck;
    }
}
