package com.cabanettes.axel.nguyen.eric.depoker;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import junit.framework.Test;

import java.util.List;

public class Accueil extends AppCompatActivity {

    //Stockage BDD
    public static DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_accueil);

        //Init BDD
        db = new DatabaseHandler(this);

        if (db.getJoueursCount() == 0) {
            db.addJoueur(new Joueur(1, "Player 1"));
            db.addJoueur(new Joueur(2, "Player 2"));
        }

        //Verification
        this.printLog();


        //Affichage sur l'accueil
        String player1 = db.getJoueur(1).getName();
        String player2 = db.getJoueur(2).getName();
        TextView t = (TextView) findViewById(R.id.versus);
        t.setText(player1 + " vs. " + player2);


        //Bouton settings
        Button btnSettings = (Button) findViewById(R.id.btnOption);
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), SettingsActivity.class);
                startActivity(intent);
            }
        });


        //Bouton play
        Button btnPlay = (Button) findViewById(R.id.btnPlay);
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer mp = MediaPlayer.create(getApplicationContext(),R.raw.sound);
                mp.start();
                /*Intent intent = new Intent(view.getContext(), SettingsActivity.class);
                startActivity(intent);*/
            }
        });

    }

    private void printLog() {
        //Log
        Log.d("Number players:", "" + db.getJoueursCount());
        Log.d("Reading: ", "Reading all players..");
        List<Joueur> joueurs = db.getAllJoueurs();
        for (Joueur joueur : joueurs) {
            String log = "Id: " + joueur.getIdJoueur() + " ,Name: " + joueur.getName();
            Log.d("Name: ", log);
        }
    }


}
