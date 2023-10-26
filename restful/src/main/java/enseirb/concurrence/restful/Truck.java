package enseirb.concurrence.restful;


import java.time.Instant;

public class Truck {
    private String licencePlate;
        private Instant firstReleaseDate;
        private int unloadedWeight;

    // Constructor without argument for JSON deserialization
    public Truck() {
    }

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
    public void setFirstReleaseDate(Instant firstReleaseDate) {
        this.firstReleaseDate = firstReleaseDate;
    }
    public void setLicencePlate(String licencePlate) {
        this.licencePlate = licencePlate;
    }

    public void setUnloadedWeight(int unloadedWeight) {
        this.unloadedWeight = unloadedWeight;
    }
}
