package com.example.worldvisit_contest;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import objects.Pays;


public class MenuActivity extends ListActivity {
    ArrayList<String> Regions = new ArrayList<>();
    ListView lv;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        Regions = this.getRegionList();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(MenuActivity.this, android.R.layout.simple_list_item_1, Regions);
        Button all = (Button) findViewById(R.id.button);

        all.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent inter = new Intent(MenuActivity.this, MainActivity.class);
                startActivity(inter);
            }
        });
        setListAdapter(adapter);
    }

    public String loadJSONFromAsset()
    {
        String json = null;
        try
        {
            InputStream is = getAssets().open("country.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
            return null;
        }

        return json;
    }

    public ArrayList<String> getRegionList (){
        ArrayList<String> items = new ArrayList<String>();
        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset());

            // Creation du tableau general a partir d'un JSONObject
            JSONArray jsonArray = obj.getJSONArray("pays");
            Pays currentPays = null;

            // Pour chaque element du tableau
            for (int i = 0; i < jsonArray.length(); i++)
            {
                // Creation d'un tableau element a partir d'un JSONObject
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                // Recuperation de l'item qui nous interesse
                String capital = jsonObj.getString("region");
                // Ajout dans l'ArrayList
                items.add(capital);
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        Set<String> mySet = new HashSet<String>(items);
        ArrayList<String> array_L2 = new ArrayList<String>(mySet);
        return array_L2;
    }


    protected void onListItemClick(ListView lv, View v, int position, long id) {
        String leContinent = (String) lv.getItemAtPosition(position);
        Intent inter = new Intent(this, MainActivity.class);
        inter.putExtra("Region", leContinent);
        startActivity(inter);
        /*Toast toast = Toast.makeText(this, leContinent, Toast.LENGTH_SHORT);
        toast.show();*/
    }


}