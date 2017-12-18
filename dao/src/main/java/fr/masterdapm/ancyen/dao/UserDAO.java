package fr.masterdapm.ancyen.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import fr.masterdapm.ancyen.model.User;

/**
 * Created by cyril on 29/11/17.
 */

public class UserDAO {
    private static final String TABLE_NAME = "user";
    private static final String COL_EMAIL = "email";
    private static final String COL_PASSWORD = "password";
    private static final String COL_LASTNAME = "lastName";
    private static final String COL_FIRSTNAME = "firstName";
    public static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +
            " (" +
            " " + COL_EMAIL + " TEXT PRIMARY KEY," +
            " " + COL_PASSWORD + " TEXT," +
            " " + COL_LASTNAME + " TEXT," +
            " " + COL_FIRSTNAME + " TEXT" +
            ");";
    private MySQLite mySQLiteBase;
    private SQLiteDatabase db;

    public UserDAO(Context context) {
        mySQLiteBase = MySQLite.getInstance(context);
    }

    public void open() {
        db = mySQLiteBase.getWritableDatabase();
    }

    public void close() {
        db.close();
    }

    public long add(User user) {
        ContentValues values = new ContentValues();
        values.put(COL_EMAIL, user.getEmail());
        values.put(COL_PASSWORD, user.getPassword());
        values.put(COL_LASTNAME, user.getLastName());
        values.put(COL_FIRSTNAME, user.getFirstName());

        // insert() retourne l'id du nouvel enregistrement inséré, ou -1 en cas d'erreur
        return db.insert(TABLE_NAME, null, values);
    }

    public int mod(User user) {

        ContentValues values = new ContentValues();
        values.put(COL_PASSWORD, user.getPassword());
        values.put(COL_LASTNAME, user.getLastName());
        values.put(COL_FIRSTNAME, user.getFirstName());

        String where = COL_EMAIL + " = ?";
        String[] whereArgs = {user.getEmail() + ""};

        // valeur de retour : (int) nombre de lignes affectées par la requête
        return db.update(TABLE_NAME, values, where, whereArgs);
    }

    public int del(String id) {

        String where = COL_EMAIL + " = ?";
        String[] whereArgs = {id + ""};

        // valeur de retour : (int) nombre de lignes affectées par la clause WHERE, 0 sinon
        return db.delete(TABLE_NAME, where, whereArgs);
    }

    public User get(String id) {

        User u = null;

        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COL_EMAIL + "=" + id, null);
        if (c.moveToFirst()) {
            u = new User(id,
                    c.getString(c.getColumnIndex(COL_PASSWORD)),
                    c.getString(c.getColumnIndex(COL_LASTNAME)),
                    c.getString(c.getColumnIndex(COL_FIRSTNAME)));
            c.close();
        }

        // Retourne l'animal dont l'id est passé en paramètre
        return u;
    }

    public Cursor getAll() {
        // sélection de tous les enregistrements de la table
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }


}