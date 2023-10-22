package enseirb.concurrence.restful;


import java.time.Instant;

public class Truck {
    private String licencePlate;
    private Instant firstReleaseDate;
    private int unloadedWeight;

    public Truck(String licencePlate, Instant firstReleaseDate, int unloadedWeight){
        this.licencePlate = licencePlate;
        this.firstReleaseDate = firstReleaseDate;
        this.unloadedWeight = unloadedWeight;
    }

    public int getUnloadedWeight() {
        return unloadedWeight;
    }
    public Instant getFirstReleaseDate() {
        return firstReleaseDate;
    }
    public String getLicencePlate() {
        return licencePlate;
    }
}
