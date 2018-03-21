package com.cabanettes.axel.nguyen.eric.depoker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class turnNumber extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences preferences = getSharedPreferences("prefs", MODE_PRIVATE);
        boolean useDarkTheme = preferences.getBoolean("dark_theme", false);

        if(useDarkTheme) {
            setTheme(R.style.AppTheme_Dark);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turn_number);

        //Gestion du groupe radio de nombre de tours


        Button btnOk = (Button) findViewById(R.id.btnTurnOK);
        final RadioGroup radioTurnGroup = (RadioGroup) findViewById(R.id.radioTurnGroup);
        btnOk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                int id = radioTurnGroup.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) findViewById(id);
                int value = 1;
                switch (radioButton.getText().toString()){
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
}
