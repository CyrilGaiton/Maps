package fr.masterdapm.ancyen.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import fr.masterdapm.ancyen.model.Position;
import fr.masterdapm.ancyen.model.Ride;
import fr.masterdapm.ancyen.model.Waypoint;

/**
 * Created by cyril on 01/12/17.
 */

public class RideDAO {
    private static final String TABLE_NAME = "ride";
    private static final String COL_ID="id";
    private static final String COL_IDORGANIZER="idOrganizer";
    private static final String COL_DEPARTUREPLACE="departurePlace";
    private static final String COL_DEPRTUREDATE="departureDate";
    private static final String COL_DEPARTUREHOUR="departureHour";
    private static final String COL_ARRIVALPLACE="arrivalPlace";
    private static final String COL_DISTANCE="distance";
    private static final String COL_DURATION="duration";
    private static final String COL_POSITIONS="positions";
    private static final String COL_WAYPOINTS="waypoints";
    private static final String COL_AUTORISEDEMAILS="autorisedEmails";
    public static final String SQL_CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+
            " (" +
            " "+COL_ID+" INTEGER primary key," +
            " "+COL_IDORGANIZER+" INTEGER," +
            " "+COL_DEPARTUREPLACE+" TEXT," +
            " "+COL_DEPRTUREDATE+" TEXT," +
            " "+COL_DEPARTUREHOUR+" TEXT," +
            " "+COL_ARRIVALPLACE+" TEXT," +
            " "+COL_DISTANCE+" TEXT," +
            " "+COL_DURATION+" TEXT," +
            " "+COL_POSITIONS+" BLOB," +
            " "+COL_WAYPOINTS+" BLOB," +
            " "+COL_AUTORISEDEMAILS+" BLOB," +
            " "+"FOREIGN KEY ("+COL_IDORGANIZER+") REFERENCES user (id)"+
            ");";
    private MySQLite mySQLiteBase;
    private SQLiteDatabase db;

    public RideDAO(Context context)
    {
        mySQLiteBase = MySQLite.getInstance(context);
    }

    public void open()
    {
        db = mySQLiteBase.getWritableDatabase();
    }

    public void close()
    {
        db.close();
    }

    public long add(Ride ride) {
        ContentValues values = new ContentValues();
        values.put(COL_ID, ride.getId());
        values.put(COL_IDORGANIZER, ride.getIdOrganizer());
        values.put(COL_DEPARTUREPLACE, ride.getDeparturePlace());
        values.put(COL_DEPRTUREDATE, ride.getDepartureDate());
        values.put(COL_DEPARTUREHOUR, ride.getDepartureHour());
        values.put(COL_ARRIVALPLACE, ride.getArrivalPlace());
        values.put(COL_DISTANCE, ride.getDistance());
        values.put(COL_DURATION, ride.getDuration());
        values.put(COL_POSITIONS, toByteArray(ride.getPositions()));
        values.put(COL_WAYPOINTS, toByteArray(ride.getWaypoints()));
        values.put(COL_AUTORISEDEMAILS, toByteArray(ride.getAutorisedEmails()));

        // insert() retourne l'id du nouvel enregistrement inséré, ou -1 en cas d'erreur
        return db.insert(TABLE_NAME,null,values);
    }

    public int mod(Ride ride) {
        ContentValues values = new ContentValues();
        values.put(COL_IDORGANIZER, ride.getIdOrganizer());
        values.put(COL_DEPARTUREPLACE, ride.getDeparturePlace());
        values.put(COL_DEPRTUREDATE, ride.getDepartureDate());
        values.put(COL_DEPARTUREHOUR, ride.getDepartureHour());
        values.put(COL_ARRIVALPLACE, ride.getArrivalPlace());
        values.put(COL_DISTANCE, ride.getDistance());
        values.put(COL_DURATION, ride.getDuration());
        values.put(COL_POSITIONS, toByteArray(ride.getPositions()));
        values.put(COL_WAYPOINTS, toByteArray(ride.getWaypoints()));
        values.put(COL_AUTORISEDEMAILS, toByteArray(ride.getAutorisedEmails()));

        String where = COL_ID+" = ?";
        String[] whereArgs = {ride.getId()+""};

        // valeur de retour : (int) nombre de lignes affectées par la requête
        return db.update(TABLE_NAME, values, where, whereArgs);
    }

    public int del(int i) {

        String where = COL_ID+" = ?";
        String[] whereArgs = {i+""};

        // valeur de retour : (int) nombre de lignes affectées par la clause WHERE, 0 sinon
        return db.delete(TABLE_NAME, where, whereArgs);
    }

    public Ride get(int id) {

        Ride r = null;

        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+COL_ID+"="+id, null);
        if (c.moveToFirst()) {
            r = new Ride(c.getInt(c.getColumnIndex(COL_ID)),
                    c.getInt(c.getColumnIndex(COL_IDORGANIZER)),
                    c.getString(c.getColumnIndex(COL_DEPARTUREPLACE)),
                    c.getString(c.getColumnIndex(COL_DEPRTUREDATE)),
                    c.getString(c.getColumnIndex(COL_DEPARTUREHOUR)),
                    c.getString(c.getColumnIndex(COL_ARRIVALPLACE)),
                    c.getString(c.getColumnIndex(COL_DISTANCE)),
                    c.getString(c.getColumnIndex(COL_DURATION)),
                    toPositions(c.getBlob(c.getColumnIndex(COL_POSITIONS))),
                    toWaypoints(c.getBlob(c.getColumnIndex(COL_WAYPOINTS))),
                    toStrings(c.getBlob(c.getColumnIndex(COL_AUTORISEDEMAILS))));
            c.close();
        }

        // Retourne l'animal dont l'id est passé en paramètre
        return r;
    }

    public Cursor getAll() {
        // sélection de tous les enregistrements de la table
        return db.rawQuery("SELECT * FROM "+TABLE_NAME, null);
    }

    public Ride[] getWithEmail(String email){
        List<Ride> rides = new ArrayList<>();
        Cursor c = getAll();
        Ride r;
        while (c.moveToNext()){
            String[] emails = toStrings(c.getBlob(c.getColumnIndex(COL_AUTORISEDEMAILS)));
            for (String e:emails
                 ) {
                if (e.equals(email)){
                    r = new Ride(c.getInt(c.getColumnIndex(COL_ID)),
                            c.getInt(c.getColumnIndex(COL_IDORGANIZER)),
                            c.getString(c.getColumnIndex(COL_DEPARTUREPLACE)),
                            c.getString(c.getColumnIndex(COL_DEPRTUREDATE)),
                            c.getString(c.getColumnIndex(COL_DEPARTUREHOUR)),
                            c.getString(c.getColumnIndex(COL_ARRIVALPLACE)),
                            c.getString(c.getColumnIndex(COL_DISTANCE)),
                            c.getString(c.getColumnIndex(COL_DURATION)),
                            toPositions(c.getBlob(c.getColumnIndex(COL_POSITIONS))),
                            toWaypoints(c.getBlob(c.getColumnIndex(COL_WAYPOINTS))),
                            toStrings(c.getBlob(c.getColumnIndex(COL_AUTORISEDEMAILS))));
                    rides.add(r);
                }
            }
        }
        c.close();
        Ride[] rides1 = new Ride[rides.size()];
        for (int i=0; i<rides.size(); i++){
            rides1[i] = rides.get(i);
        }
        return rides1;
    }


    public byte[] toByteArray(Object[] o) {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        byte[] b = null;
        try {
            ObjectOutputStream oos = new ObjectOutputStream(bout);
            for (int i=0; i<o.length; i++){
                oos.writeObject(o[i]);
            }
            b = bout.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return b;
    }

    public Position[] toPositions(byte[] b) {
        ByteArrayInputStream binp = new ByteArrayInputStream(b);
        List<Position> positions = new ArrayList<>();
        try {
            ObjectInputStream oos = new ObjectInputStream(binp);
            while (oos.available() > 0){
                positions.add((Position) oos.readObject());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Position[] p = new Position[positions.size()];
        for (int i=0; i<positions.size(); i++){
            p[i] = positions.get(i);
        }
        return p;
    }

    public Waypoint[] toWaypoints(byte[] b) {
        ByteArrayInputStream binp = new ByteArrayInputStream(b);
        List<Waypoint> waypoints = new ArrayList<>();
        try {
            ObjectInputStream oos = new ObjectInputStream(binp);
            while (oos.available() > 0){
                waypoints.add((Waypoint) oos.readObject());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Waypoint[] w = new Waypoint[waypoints.size()];
        for (int i=0; i<waypoints.size(); i++){
            w[i] = waypoints.get(i);
        }
        return w;
    }

    public String[] toStrings(byte[] b) {
        ByteArrayInputStream binp = new ByteArrayInputStream(b);
        List<String> strings = new ArrayList<>();
        try {
            ObjectInputStream oos = new ObjectInputStream(binp);
            while (oos.available() > 0){
                strings.add((String) oos.readObject());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String[] s = new String[strings.size()];
        for (int i=0; i<strings.size(); i++){
            s[i] = strings.get(i);
        }
        return s;
    }

}
