package enseirb.concurrence.restful;

import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class TruckService {
    private HashMap<Integer, Truck> trucks = new HashMap<Integer, Truck>();

    public TruckService(){}

    public void addTruck(Truck truck){
        trucks.put(truck.getTruckId(), truck);
    }

    public Position getTruckPosition(int id) throws IllegalAccessException {
        if(trucks.get(id) == null)
            throw new IllegalAccessException("Truck id doesn't exist");
        return trucks.get(id).getPosition();
    }

}
