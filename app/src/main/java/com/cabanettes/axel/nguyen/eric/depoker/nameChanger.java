package com.cabanettes.axel.nguyen.eric.depoker;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class nameChanger extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Initialisation du Dark theme
        SharedPreferences preferences = getSharedPreferences("prefs", MODE_PRIVATE);
        boolean useDarkTheme = preferences.getBoolean("dark_theme", false);
        if(useDarkTheme) {
            setTheme(R.style.AppTheme_Dark);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_namechanger);


        //Bouton enregistrer
        Button btnRegister = (Button) findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerPlayers(view);
            }
        });

        //Gestion de l'affichage des joueurs s'ils existent
        if (Accueil.db.getJoueursCount() != 0) {
            EditText e1 = (EditText) findViewById(R.id.editTextPlayer1);
            EditText e2 = (EditText) findViewById(R.id.editTextPlayer2);
            String player1 = Accueil.db.getJoueur(1).getName();
            String player2 = Accueil.db.getJoueur(2).getName();
            e1.setText(player1);
            e2.setText(player2);
        }
    }

    //Action du bouton enregistrer
    protected void registerPlayers(View view){
        EditText joueur1 = (EditText) findViewById(R.id.editTextPlayer1);
        EditText joueur2 = (EditText) findViewById(R.id.editTextPlayer2);
        String nomJoueur1 = joueur1.getText().toString();
        String nomJoueur2 = joueur2.getText().toString();
        Joueur j1 = new Joueur(1,nomJoueur1);
        Joueur j2 = new Joueur(2,nomJoueur2);

        //Création de 2 joueurs s'il n'existent pas encore
        //Sinon met à jour les noms
        if(Accueil.db.getJoueursCount()==0) {
            Log.d("Insert: ", "Inserting ..");
            Accueil.db.addJoueur(j1);
            Accueil.db.addJoueur(j2);
        } else {
            Log.d("Update: ", "Updating ..");
            Accueil.db.updateJoueur(j1);
            Accueil.db.updateJoueur(j2);
        }

        Toast.makeText(view.getContext(), getResources().getIdentifier("nameChanged","string",view.getContext().getPackageName()), Toast.LENGTH_SHORT).show();
        finish();
    }
}
