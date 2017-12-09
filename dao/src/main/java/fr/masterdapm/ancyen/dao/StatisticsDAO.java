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

import fr.masterdapm.ancyen.model.Statistics;
import fr.masterdapm.ancyen.model.TimedPosition;

/**
 * Created by cyril on 01/12/17.
 */

public class StatisticsDAO {
    private static final String TABLE_NAME = "statistics";
    private static final String COL_IDRIDE="idRide";
    private static final String COL_IDUSER="idUser";
    private static final String COL_TIMEDPOSITIONS="timedPositions";
    public static final String SQL_CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+
            " (" +
            " "+COL_IDRIDE+" INTEGER ," +
            " "+COL_IDUSER+" INTEGER ," +
            " "+COL_TIMEDPOSITIONS+" BLOB," +
            " "+"FOREIGN KEY ("+COL_IDRIDE+") REFERENCES ride (id)," +
            " "+"FOREIGN KEY ("+COL_IDUSER+") REFERENCES user (id)," +
            " "+"PRIMARY KEY ("+COL_IDRIDE+", "+COL_IDUSER+")" +
            ");";
    private MySQLite mySQLiteBase;
    private SQLiteDatabase db;

    public StatisticsDAO(Context context)
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

    public long add(Statistics statistics) {
        ContentValues values = new ContentValues();
        values.put(COL_IDRIDE, statistics.getIdRide());
        values.put(COL_IDUSER, statistics.getIdUser());
        values.put(COL_TIMEDPOSITIONS, toByteArray(statistics.getTimedPositions()));

        // insert() retourne l'id du nouvel enregistrement inséré, ou -1 en cas d'erreur
        return db.insert(TABLE_NAME,null,values);
    }

    public int mod(Statistics statistics) {
        ContentValues values = new ContentValues();
        values.put(COL_TIMEDPOSITIONS, toByteArray(statistics.getTimedPositions()));

        String where = COL_IDRIDE+" = ? AND "+COL_IDUSER+" = ? ";
        String[] whereArgs = {statistics.getIdRide()+"", statistics.getIdUser()+""};

        // valeur de retour : (int) nombre de lignes affectées par la requête
        return db.update(TABLE_NAME, values, where, whereArgs);
    }

    public int del(int i1, int i2) {

        String where = COL_IDRIDE+" = ? AND "+COL_IDUSER+" = ? ";
        String[] whereArgs = {i1+"", i2+""};

        // valeur de retour : (int) nombre de lignes affectées par la clause WHERE, 0 sinon
        return db.delete(TABLE_NAME, where, whereArgs);
    }

    public Statistics get(int idRide, int idUser) {

        Statistics s = null;

    Cursor c = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+COL_IDRIDE+"="+idRide+" AND "+COL_IDUSER+"="+idUser, null);
        if (c.moveToFirst()) {
            s = new Statistics(idRide,
                    idUser,
                    toTimedPositions(c.getBlob(c.getColumnIndex(COL_TIMEDPOSITIONS))));
            c.close();
        }

        // Retourne l'animal dont l'id est passé en paramètre
        return s;
    }

    public Cursor getAll() {
        // sélection de tous les enregistrements de la table
        return db.rawQuery("SELECT * FROM "+TABLE_NAME, null);
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

    public TimedPosition[] toTimedPositions(byte[] b) {
        ByteArrayInputStream binp = new ByteArrayInputStream(b);
        List<TimedPosition> timedpositions = new ArrayList<>();
        try {
            ObjectInputStream oos = new ObjectInputStream(binp);
            while (oos.available() > 0){
                timedpositions.add((TimedPosition) oos.readObject());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        TimedPosition[] tp = new TimedPosition[timedpositions.size()];
        for (int i=0; i<timedpositions.size(); i++){
            tp[i] = timedpositions.get(i);
        }
        return tp;
    }

}