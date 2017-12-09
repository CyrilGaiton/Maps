package fr.masterdapm.ancyen.model;

import java.io.Serializable;

/**
 * Created by cyril on 25/11/17.
 */

public class Position implements Serializable{
    private int longitude;
    private int latitude;
    private int elevation;

    public Position(int longitude, int latitude, int elevation) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.elevation = elevation;
    }

    public int getLongitude() {
        return longitude;
    }

    public int getLatitude() {
        return latitude;
    }

    public int getElevation() {
        return elevation;
    }

}
