package com.cabanettes.axel.nguyen.eric.depoker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class turnNumber extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Initialisation du Dark theme
        SharedPreferences preferences = getSharedPreferences("prefs", MODE_PRIVATE);
        boolean useDarkTheme = preferences.getBoolean("dark_theme", false);
        if (useDarkTheme) {
            setTheme(R.style.AppTheme_Dark);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turn_number);

        //Gestion du groupe radio de nombre de tours
        int radioButtonSelected = preferences.getInt("turnNumber", 3);
        Log.d("Radio button", "" + radioButtonSelected);
        this.setRadioChecked(radioButtonSelected);

        Button btnOk = (Button) findViewById(R.id.btnTurnOK);
        final RadioGroup radioTurnGroup = (RadioGroup) findViewById(R.id.radioTurnGroup);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = radioTurnGroup.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) findViewById(id);
                int value = 1;
                switch (radioButton.getText().toString()) {
                    case "1":
                        value = 1;
                        break;
                    case "2":
                        value = 2;
                        break;
                    case "3":
                        value = 3;
                        break;
                }
                changeValue(value);
                Toast.makeText(view.getContext(), getResources().getString(R.string.changedTurn)+" "+value, Toast.LENGTH_SHORT).show();
                finish();
            }
        });


    }

    //Change la valeur de turnNumber  dans /data/data/com.cabanettes.axel.nguyen.eric.depoker/shared_prefs/prefs.xml
    private void changeValue(int value) {
        SharedPreferences.Editor editor = getSharedPreferences("prefs", MODE_PRIVATE).edit();
        editor.putInt("turnNumber", value);
        editor.commit();

    }

    //Met à jour le radio
    private void setRadioChecked(int value) {
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioTurnGroup);
        int radioButtonId = getResources().getIdentifier("radioButton" + value, "id", getPackageName());
        Log.d("Radio button id", "" + radioButtonId);
        radioGroup.check(radioButtonId);
    }
}
