package com.cabanettes.axel.nguyen.eric.depoker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settingslayout);

        ListView liste = (ListView) findViewById(R.id.settingList);
        liste.setFocusable(false);
        List<String> valeurs = new ArrayList<>();
        for(String s : getResources().getStringArray(R.array.settings)){
            valeurs.add(s);
        }
        /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,valeurs);
        liste.setAdapter(adapter);
        liste.setDivider(null);*/
        CustomListAdapter listAdapter = new CustomListAdapter(this , R.layout.customlist, valeurs);
        liste.setAdapter(listAdapter);
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
