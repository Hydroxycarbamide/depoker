package com.cabanettes.axel.nguyen.eric.depoker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eric on 13/03/2018.
 */


public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "JoueurManager";

    // Tables
    private static final String TABLE_JOUEUR = "Joueur";

    // Colonnes
    private static final String KEY_ID = "idJoueur";
    private static final String KEY_NAME = "name";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_JOUEUR_TABLE = "CREATE TABLE " + TABLE_JOUEUR + "(" + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_NAME + " STRING" + ")";
        Log.d("Create ", "Table created");
        sqLiteDatabase.execSQL(CREATE_JOUEUR_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_JOUEUR);
        onCreate(sqLiteDatabase);
    }

    public void addJoueur(Joueur joueur) {
        SQLiteDatabase db = this.getWritableDatabase();

        //Obtient la nouvelle valeur
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, joueur.getName());

        // Insert into
        db.insert(TABLE_JOUEUR, null, values);
        db.close();
    }

    //Requête select par id
    //SELECT * FROM JOUEUR WHERE idJoueur = id
    public Joueur getJoueur(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_JOUEUR, new String[]{KEY_ID,
                        KEY_NAME}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Joueur joueur = new Joueur(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1));

        return joueur;
    }

    //SELECT * FROM JOUEUR
    public List<Joueur> getAllJoueurs() {
        List<Joueur> joueurList = new ArrayList<Joueur>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_JOUEUR;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Joueur joueur = new Joueur();
                joueur.setIdJoueur(Integer.parseInt(cursor.getString(0)));
                joueur.setName(cursor.getString(1));
                joueurList.add(joueur);
            } while (cursor.moveToNext());
        }
        return joueurList;
    }

    //UPDATE
    public int updateJoueur(Joueur joueur) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, joueur.getName());

        // updating row
        return db.update(TABLE_JOUEUR, values, KEY_ID + " = ?",
                new String[]{String.valueOf(joueur.getIdJoueur())});
    }

    //COUNT
    /*public int getJoueursCount() {
        String countQuery = "SELECT * FROM " + TABLE_JOUEUR;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        return cursor.getCount();
    }*/

    public int getJoueursCount() {
        String countQuery = "SELECT COUNT(*) FROM " + TABLE_JOUEUR;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        return count;
    }
}
