package fr.masterdapm.ancyen.DAO;

import javax.naming.Context;

import fr.masterdapm.ancyen.modele.User;

/**
 * Created by cyril on 29/11/17.
 */

public class UserDAO {
    private static final String TABLE_NAME = "user";
    public static final String COL_IDUSER="idUser";
    public static final String COL_EMAIL="email";
    public static final String COL_PASSWORD="password";
    public static final String COL_LASTNAME="lastName";
    public static final String COL_FIRSTNAME="firstName";
    public static final String SQL_CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+
            " (" +
            " "+COL_IDUSER+" INTEGER primary key AUTOINCREMENT NOT NULL," +
            " "+COL_EMAIL+" TEXT" +
            " "+COL_PASSWORD+" TEXT" +
            " "+COL_LASTNAME+" TEXT" +
            " "+COL_FIRSTNAME+" TEXT" +
            ");";
    private MySQLite mySQLiteBase;
    private SQLiteDatabase db;

    public UserDAO(Context context)
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

    public long addUser(User user) {
        ContentValues values = new ContentValues();
        values.put(KEY_NOM_ANIMAL, animal.getNom_animal());

        // insert() retourne l'id du nouvel enregistrement inséré, ou -1 en cas d'erreur
        return db.insert(TABLE_NAME,null,values);
    }

    public int modAnimal(Animal animal) {
        // modification d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la requête

        ContentValues values = new ContentValues();
        values.put(KEY_NOM_ANIMAL, animal.getNom_animal());

        String where = KEY_ID_ANIMAL+" = ?";
        String[] whereArgs = {animal.getId_animal()+""};

        return db.update(TABLE_NAME, values, where, whereArgs);
    }

    public int supAnimal(Animal animal) {
        // suppression d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la clause WHERE, 0 sinon

        String where = KEY_ID_ANIMAL+" = ?";
        String[] whereArgs = {animal.getId_animal()+""};

        return db.delete(TABLE_NAME, where, whereArgs);
    }

    public Animal getAnimal(int id) {
        // Retourne l'animal dont l'id est passé en paramètre

        Animal a=new Animal(0,"");

        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+KEY_ID_ANIMAL+"="+id, null);
        if (c.moveToFirst()) {
            a.setId_animal(c.getInt(c.getColumnIndex(KEY_ID_ANIMAL)));
            a.setNom_animal(c.getString(c.getColumnIndex(KEY_NOM_ANIMAL)));
            c.close();
        }

        return a;
    }

    public Cursor getAnimaux() {
        // sélection de tous les enregistrements de la table
        return db.rawQuery("SELECT * FROM "+TABLE_NAME, null);
    }

}
}
