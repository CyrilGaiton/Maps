package fr.masterdapm.ancyen.model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by cyril on 25/11/17.
 */

public class Ride implements Serializable{
    private final int id;
    private final int idOrganizer;
    private String departurePlace;
    private String departureDate;
    private String departureHour;
    private String arrivalPlace;
    private String distance;
    private String duration;
    private Position[] positions;
    private Waypoint[] waypoints;
    private String[] autorisedEmails;

    public Ride(int id, int idOrganizer, String departurePlace, String departureDate, String departureHour, String arrivalPlace, String distance, String duration, Position[] positions, Waypoint[] waypoints, String[] autorisedEmails) {
        this.id = id;
        this.idOrganizer = idOrganizer;
        this.departurePlace = departurePlace;
        this.departureDate = departureDate;
        this.departureHour = departureHour;
        this.arrivalPlace = arrivalPlace;
        this.distance = distance;
        this.duration = duration;
        this.positions = positions;
        this.waypoints = waypoints;
        this.autorisedEmails = autorisedEmails;
    }

    public int getId() {
        return id;
    }

    public int getIdOrganizer() {
        return idOrganizer;
    }

    public String getDeparturePlace() {
        return departurePlace;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public String getDepartureHour() {
        return departureHour;
    }

    public String getArrivalPlace() {
        return arrivalPlace;
    }

    public String getDistance() {
        return distance;
    }

    public String getDuration() {
        return duration;
    }

    public Position[] getPositions() {
        return positions;
    }

    public Waypoint[] getWaypoints() {
        return waypoints;
    }

    public String[] getAutorisedEmails() {
        return autorisedEmails;
    }
}
