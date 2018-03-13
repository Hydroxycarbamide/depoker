package com.cabanettes.axel.nguyen.eric.depoker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

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


            this.verif();

            String player1 = db.getJoueur(1).getName();
            String player2 = db.getJoueur(2).getName();

            TextView t = (TextView) findViewById(R.id.versus);
            t.setText(player1 + " vs. " + player2);
        }







    }

    private void verif(){
        //Log (Static args)
        Log.d("Number players:",""+db.getJoueursCount());
        Log.d("Reading: ", "Reading all contacts..");
        List<Joueur> joueurs = db.getAllJoueurs();
        for (Joueur joueur : joueurs) {
            String log = "Id: " + joueur.getIdJoueur() + " ,Name: " + joueur.getName();
            // Writing Contacts to log
            Log.d("Name: ", log);
        }
    }


}
