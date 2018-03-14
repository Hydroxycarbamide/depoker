package com.cabanettes.axel.nguyen.eric.depoker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import junit.framework.Test;

import java.util.List;

public class Accueil extends AppCompatActivity {

    public static DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_accueil);

        db = new DatabaseHandler(this);

        if(db.getJoueursCount()==0)
            startActivity(new Intent(this, MainActivity.class));
        else {


            this.printLog();
            String player1 = db.getJoueur(1).getName();
            String player2 = db.getJoueur(2).getName();

            TextView t = (TextView) findViewById(R.id.versus);
            t.setText(player1 + " vs. " + player2);

            Button btnSettings = (Button) findViewById(R.id.btnOption);
            btnSettings.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    runSettings();
                }
            });
        }
    }

    private void printLog(){
        //Log (Static args)
        Log.d("Number players:",""+db.getJoueursCount());
        Log.d("Reading: ", "Reading all players..");
        List<Joueur> joueurs = db.getAllJoueurs();
        for (Joueur joueur : joueurs) {
            String log = "Id: " + joueur.getIdJoueur() + " ,Name: " + joueur.getName();
            Log.d("Name: ", log);
        }
    }

    protected void runSettings(){
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

}
