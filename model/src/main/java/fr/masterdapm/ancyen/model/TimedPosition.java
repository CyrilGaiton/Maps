package fr.masterdapm.ancyen.model;

import java.io.Serializable;

/**
 * Created by cyril on 25/11/17.
 */

public class TimedPosition extends Position implements Serializable{
    private String time;

    public TimedPosition(int longitude, int latitude, int elevation, String time) {
        super(longitude, latitude, elevation);
        this.time = time;
    }

    public String getTime() {
        return time;
    }

}
