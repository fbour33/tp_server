package enseirb.concurrence.restful;


import java.time.Instant;

public class Truck {
    private final int truckId;
    private Instant ts;
    private Position position;

    public Truck(int truckId, Instant ts, Position position) {
        this.truckId = truckId;
        this.ts = ts;
        this.position = position;
    }

    public int getTruckId() {
        return truckId;
    }

    public Instant getTs() {
        return ts;
    }

    public double getLongitude() {
        return position.getLongitude();
    }

    public double getLatitude() {
        return position.getLatitude();
    }

    public Position getPosition() {
        return position;
    }

    public void setTs(Instant ts) {
        this.ts = ts;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
