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



        Button btnRegister = (Button) findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerPlayers(view);
            }
        });
    }

    protected void registerPlayers(View view){
        Intent intent = new Intent(this, Accueil.class);
        intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
        EditText joueur1 = (EditText) findViewById(R.id.editTextPlayer1);
        EditText joueur2 = (EditText) findViewById(R.id.editTextPlayer2);
        String nomJoueur1 = joueur1.getText().toString();
        String nomJoueur2 = joueur2.getText().toString();
        Joueur j1 = new Joueur(nomJoueur1);
        Joueur j2 = new Joueur(nomJoueur2);
        Log.d("Insert: ", "Inserting ..");
        Accueil.db.addJoueur(j1);
        Accueil.db.addJoueur(j2);


        startActivity(intent);


    }
}
