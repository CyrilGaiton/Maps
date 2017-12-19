package fr.masterdapm.ancyen.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by cyril on 01/12/17.
 */

public class MySQLite extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "database_map";
    private static final int DATABASE_VERSION = 4;
    private static MySQLite instance = null;

    public synchronized static MySQLite getInstance(Context context) {
        if (instance == null) {
            Log.e("lolmdr", "ALEDDDDDDDDDD");
            instance = new MySQLite(context); }
        return instance;
    }

    private MySQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Création de la base de données
        // on exécute ici les requêtes de création des tables
        Log.e("lolmdr", "oncreate");
        sqLiteDatabase.execSQL(UserDAO.SQL_CREATE_TABLE);
        sqLiteDatabase.execSQL(RideDAO.SQL_CREATE_TABLE);
        sqLiteDatabase.execSQL(StatisticsDAO.SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        // Mise à jour de la base de données
        // méthode appelée sur incrémentation de DATABASE_VERSION

        sqLiteDatabase.execSQL(UserDAO.SQL_DROP_TABLE);
        sqLiteDatabase.execSQL(RideDAO.SQL_DROP_TABLE);
        sqLiteDatabase.execSQL(StatisticsDAO.SQL_DROP_TABLE);
        onCreate(sqLiteDatabase);
    }

}
