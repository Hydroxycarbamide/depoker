package com.cabanettes.axel.nguyen.eric.depoker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {


    private static final String PREFS_NAME = "prefs";
    private static final String PREF_DARK_THEME = "dark_theme";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean useDarkTheme = preferences.getBoolean(PREF_DARK_THEME, false);

        if(useDarkTheme) {
            setTheme(R.style.AppTheme_Dark);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.settingslayout);

        //Intent  test
        Bundle extras = getIntent().getExtras();
        if(extras!=null)
        {
            Integer numberTurn =(Integer) extras.get("numberTurn");
            Log.d("Number of turn",""+numberTurn.toString());
        }

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
                        Intent activity_turn = new Intent(view.getContext(), turnNumber.class);
                        startActivity(activity_turn);
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

        //Gestion du switch de l'actionbar
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


}
