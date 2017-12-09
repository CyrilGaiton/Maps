package fr.masterdapm.ancyen.model;

import java.io.Serializable;

/**
 * Created by cyril on 25/11/17.
 */

public class Statistics implements Serializable{
    private final int idRide;
    private final int idUser;
    private TimedPosition[] timedPositions;

    public Statistics(int idRide, int idUser, TimedPosition[] timedPositions) {
        this.idRide = idRide;
        this.idUser = idUser;
        this.timedPositions = timedPositions;
    }

    public int getIdRide() {
        return idRide;
    }

    public int getIdUser() {
        return idUser;
    }

    public TimedPosition[] getTimedPositions() {
        return timedPositions;
    }
}
