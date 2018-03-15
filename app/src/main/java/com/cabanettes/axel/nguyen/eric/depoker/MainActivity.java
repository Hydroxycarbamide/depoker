package com.cabanettes.axel.nguyen.eric.depoker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Bouton enregistrer
        Button btnRegister = (Button) findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerPlayers(view);
            }
        });

        if (Accueil.db.getJoueursCount() != 0) {
            EditText e1 = (EditText) findViewById(R.id.editTextPlayer1);
            EditText e2 = (EditText) findViewById(R.id.editTextPlayer2);
            String player1 = Accueil.db.getJoueur(1).getName();
            String player2 = Accueil.db.getJoueur(2).getName();
            e1.setText(player1);
            e2.setText(player2);
        }
    }

    protected void registerPlayers(View view){
        Intent intent = new Intent(this, Accueil.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        EditText joueur1 = (EditText) findViewById(R.id.editTextPlayer1);
        EditText joueur2 = (EditText) findViewById(R.id.editTextPlayer2);
        String nomJoueur1 = joueur1.getText().toString();
        String nomJoueur2 = joueur2.getText().toString();
        Joueur j1 = new Joueur(1,nomJoueur1);
        Joueur j2 = new Joueur(2,nomJoueur2);

        if(Accueil.db.getJoueursCount()==0) {
            Log.d("Insert: ", "Inserting ..");
            Accueil.db.addJoueur(j1);
            Accueil.db.addJoueur(j2);
        } else {
            Log.d("Update: ", "Updating ..");
            Accueil.db.updateJoueur(j1);
            Accueil.db.updateJoueur(j2);
        }
        startActivity(intent);
    }
}
