package com.cabanettes.axel.nguyen.eric.depoker;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class Accueil extends AppCompatActivity {

    static final int REQUEST_CODE = 1;
    //Stockage BDD
    public static DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences preferences = getSharedPreferences("prefs", MODE_PRIVATE);
        boolean useDarkTheme = preferences.getBoolean("dark_theme", false);

        if (useDarkTheme) {
            setTheme(R.style.AppTheme_Dark_NoActionBar);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_accueil);
        //Init var
        final String[] values = {"1", "2", "3"};
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

        //Bouton play
        Button btnPlay = (Button) findViewById(R.id.btnPlay);
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.sound);
                mp.start();
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        //Bouton settings
        Button btnSettings = (Button) findViewById(R.id.btnOption);
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), SettingsActivity.class);
                startActivity(intent);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("Result Code", "" + resultCode);
        Log.d("Request Code", "" + requestCode);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Log.d("Everything is", "OK");
                int winner = data.getIntExtra("winner", 0);
                victoryDialog(winner);

            }
        }
    }

    private void victoryDialog(int playerNum) {
        Log.d("Dialog", "Starting...");
        AlertDialog.Builder builder1 = new AlertDialog.Builder(Accueil.this);
        String winner = "Personne n'";
        if(playerNum != 0) {
            Joueur joueur = db.getJoueur(playerNum);
            winner = joueur.getName()+" ";
        }
        builder1.setTitle(winner + "a gagné !");
        builder1.setMessage("Voulez-vous rejouer ?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivityForResult(intent, REQUEST_CODE);
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
}
