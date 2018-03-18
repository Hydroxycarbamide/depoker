package com.cabanettes.axel.nguyen.eric.depoker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settingslayout);

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
                    case 0:  Intent newActivity = new Intent(view.getContext(), nameChanger.class);
                        startActivity(newActivity);
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

    }

    //Creation du menu
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
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
