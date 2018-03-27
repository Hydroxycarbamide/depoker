package com.cabanettes.axel.nguyen.eric.depoker;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {


    private static final String PREFS_NAME = "prefs";
    private static final String PREF_DARK_THEME = "dark_theme";
    private static final String PREF_TURNNUMBER = "turnNumber";
    private int valueTurn;
    private AlertDialog alertDialog1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Initialisation du Dark theme
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean useDarkTheme = preferences.getBoolean(PREF_DARK_THEME, false);
        if(useDarkTheme) {
            setTheme(R.style.AppTheme_Dark);
        }
        //Initialisation du nombre de tour
        valueTurn = preferences.getInt(PREF_TURNNUMBER, 3);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.settingslayout);

        final String[] values = {"1", "2", "3"};

        //Liste concernant la categorie GENERAL
        ListView listeGeneral = (ListView) findViewById(R.id.generalList);
        listeGeneral.setFocusable(true);
        listeGeneral.setDivider(null);
        String[] valeurs = getResources().getStringArray(R.array.settingsGeneral);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,valeurs);
        listeGeneral.setAdapter(adapter);
        listeGeneral.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                switch(position){
                    case 0:
                        Intent activity_name = new Intent(view.getContext(), nameChanger.class);
                        startActivity(activity_name);
                        break;
                    case 1:
                        radioButtonPopup(values);
                        break;
                }
            }
        });

        //Liste concernant la categorie OTHER
        ListView listeOther = (ListView) findViewById(R.id.otherList);
        listeOther.setFocusable(true);
        listeOther.setDivider(null);
        String[] valeursOther = getResources().getStringArray(R.array.settingsAbout);
        ArrayAdapter<String> adapterOther = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,valeursOther);
        listeOther.setAdapter(adapterOther);
        listeOther.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                switch(position){
                    case 0:  Intent newActivity = new Intent(view.getContext(), AboutActivity.class);
                        startActivity(newActivity);
                        break;
                }
            }
        });
        //Gestion de l'affichage de la version
        TextView version = (TextView) findViewById(R.id.version);
        version.setText(""+BuildConfig.VERSION_NAME);




    }

    //Gestion de l'action du toggleButton
    private void toggleTheme(boolean darkTheme) {
        SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
        editor.putBoolean(PREF_DARK_THEME, darkTheme);
        editor.apply();

        Intent intent = getIntent();
        finish();

        startActivity(intent);
    }

    //Creation du menu
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        //Gestion du toggle button de l'actionbar
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean useDarkTheme = preferences.getBoolean(PREF_DARK_THEME, false);
        Switch toggle = (Switch) menu.findItem(R.id.app_bar_switch_light).getActionView().findViewById(R.id.switchLight);
        toggle.setChecked(useDarkTheme);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton view, boolean isChecked) {
                toggleTheme(isChecked);
                Toast.makeText(view.getContext(), getResources().getString(R.string.launchApp), Toast.LENGTH_SHORT).show();
            }
        });
        return true;
    }

    //Configuration du menu
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                startActivity(new Intent(SettingsActivity.this, AboutActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void radioButtonPopup(String[] values) {
        Log.d("Turn value", ""+this.valueTurn);
        AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
        builder.setTitle(getResources().getIdentifier("selectNumberTurn","string",getApplicationContext().getPackageName()));
        builder.setSingleChoiceItems(values, valueTurn-1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                changeValue(item+1);
                Toast.makeText(SettingsActivity.this, getResources().getString(R.string.changedTurn)+" "+valueTurn, Toast.LENGTH_SHORT).show();
                alertDialog1.dismiss();
            }
        });
        alertDialog1 = builder.create();
        alertDialog1.show();
    }

    //Change la valeur de turnNumber  dans /data/data/com.cabanettes.axel.nguyen.eric.depoker/shared_prefs/prefs.xml
    private void changeValue(int value) {
        SharedPreferences.Editor editor = getSharedPreferences("prefs", MODE_PRIVATE).edit();
        editor.putInt(PREF_TURNNUMBER, value);
        editor.commit();
        valueTurn = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).getInt(PREF_TURNNUMBER, 3);
    }


}
