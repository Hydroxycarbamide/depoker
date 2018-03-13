import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Eric on 13/03/2018.
 */


public class DatabaseHandler extends SQLiteOpenHelper {

    // Database Name
    private static final String DATABASE_NAME = "JoueurManager";

    // Tables
    private static final String TABLE_JOUEUR = "Joueur";

    // Colonnes
    private static final String KEY_ID = "idJoueur";
    private static final String KEY_NAME = "name";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String  CREATE_JOUEUR_TABLE = "CREATE TABLE "+TABLE_JOUEUR+"("+ KEY_ID + " INTEGER PRIMARY KEY,"+ KEY_NAME+"TEXT)";
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

        Cursor cursor = db.query(TABLE_JOUEUR, new String[] { KEY_ID,
                        KEY_NAME}, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Joueur joueur = new Joueur(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1));

        return joueur;
    }

    //UPDATE
    public int updateContact(Joueur joueur) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, joueur.getName());

        // updating row
        return db.update(TABLE_JOUEUR, values, KEY_ID + " = ?",
                new String[] { String.valueOf(joueur.getIdJoueur()) });
    }
}
