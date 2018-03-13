package com.cabanettes.axel.nguyen.eric.depoker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class Accueil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_accueil);

        Intent intent = getIntent();
        String player1 = intent.getStringExtra(MainActivity.PLAYER1);
        String player2 = intent.getStringExtra(MainActivity.PLAYER2);

    }



}
