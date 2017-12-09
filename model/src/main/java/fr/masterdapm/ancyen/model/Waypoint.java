package fr.masterdapm.ancyen.model;

import java.io.Serializable;

/**
 * Created by cyril on 25/11/17.
 */

public class Waypoint extends Position implements Serializable{
    private String description;

    public Waypoint(int longitude, int latitude, int elevation, String description) {
        super(longitude, latitude, elevation);
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
