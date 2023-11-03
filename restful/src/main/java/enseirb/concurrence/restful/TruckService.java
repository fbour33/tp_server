package enseirb.concurrence.restful;

import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class TruckService {
    private HashMap<Integer, Truck> trucksss = new HashMap<Integer, Truck>();

    public TruckService(){}

    public void addTruck(Truck truck){
        trucksss.put(truck.getTruckId(), truck);
    }

    public Position getTruckPosition(int id){
        return trucksss.get(id).getPosition();
    }

}
