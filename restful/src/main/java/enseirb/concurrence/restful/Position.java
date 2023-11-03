package enseirb.concurrence.restful;

import java.awt.*;

public class Position {

    private double longitude;
    private double latitude;

    public Position(double longitude, double latitude){
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }
}
