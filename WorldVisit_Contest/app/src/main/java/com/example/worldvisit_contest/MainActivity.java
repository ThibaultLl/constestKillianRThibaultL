package com.example.worldvisit_contest;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import adapters.PaysAdapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.os.Bundle;
import android.view.View;

import objects.Pays;

public class MainActivity extends Activity {
    ListView lv;
    ArrayList<Pays> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        lv = (ListView) findViewById(R.id.list);
        items = new ArrayList<Pays>();

        Intent inter = getIntent();
        //Log.i("tag3", "test" + inter.getStringExtra("Region"));
        if (inter.getStringExtra("Region") == null) {
            //Toast.makeText(this, "rien", Toast.LENGTH_LONG).show();
            items = getCountryList();
        } else {
            //Toast.makeText(this, inter.getStringExtra("Region"), Toast.LENGTH_LONG).show();
            items = getCountryListByRegion(inter.getStringExtra("Region"));
        }

        PaysAdapter adapter = new PaysAdapter(MainActivity.this,R.layout.row,items,MainActivity.this);
        final EditText filter = (EditText) findViewById(R.id.search);
        filter.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){
                ArrayList<Pays> CountryFilter = new ArrayList<>();
                for (Pays lePays : items)
                {
                    if (lePays.getNomPays().toLowerCase().contains(s))
                    {
                        CountryFilter.add(lePays);
                    }
                }
                PaysAdapter adapterFilter = new PaysAdapter(MainActivity.this,R.layout.row,CountryFilter,MainActivity.this);
                lv.setAdapter(adapterFilter);
            }
        });
        lv.setAdapter(adapter);


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Pays lePays = (Pays) lv.getItemAtPosition(position);
                AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                builder1.setTitle(lePays.getNomPays());
                builder1.setIcon(MainActivity.this.getResources().getIdentifier("com.example.geoworld:drawable/" + lePays.getDrapeau(), null, null));
                builder1.setMessage("Capitale : " + lePays.getCapital() + "\nContinent : " + lePays.getContinent());
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });
    }

    public void Go(View v) {
        //on creer une nouvelle intent on definit la class de depart ici this et la class d'arrivé ici SecondActivite
        Intent intent=new Intent(this,AjouterPays.class);
        //on lance l'intent, cela a pour effet de stoper l'activité courante et lancer une autre activite ici SecondActivite
        startActivity(intent);
    }

    public ArrayList<Pays> getCountryList (){
        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset());

            // Creation du tableau general a partir d'un JSONObject
            JSONArray jsonArray = obj.getJSONArray("pays");
            //Pays currentPays = null;
            //Log.i("tag2", "taille" + jsonArray.length());
            // Pour chaque element du tableau
            for (int i = 0; i < jsonArray.length(); i++)
            {
                // Creation d'un tableau element a partir d'un JSONObject
                JSONObject jsonObj = jsonArray.getJSONObject(i);

                // Recuperation de l'item qui nous interesse
                JSONObject translations = jsonObj.getJSONObject("translations");
                JSONObject french = translations.getJSONObject("fra");
                // JSONObject name = jsonObj.getJSONObject("name");
                String nomPays = french.getString("common");
                //Log.i("tag5", nomPays);
                String capital = jsonObj.getString("capital");
                //Log.i("tag6", capital);
                String drapeau = jsonObj.getString("cca2").toLowerCase();
                //Log.i("tag7", drapeau);
                String continent = jsonObj.getString("region");
                Pays currentPays = new Pays(nomPays, capital, drapeau, continent);
                // Ajout dans l'ArrayList
                //Log.i("tag4", currentPays.toString());
                items.add(currentPays);
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return items;
    }

    public ArrayList<Pays> getCountryListByRegion(String filter){
        ArrayList<Pays> items = new ArrayList<Pays>();
        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset());

            // Creation du tableau general a partir d'un JSONObject
            JSONArray jsonArray = obj.getJSONArray("pays");
            Pays currentPays = null;
            //Log.i ("tag2", "taille :" + jsonArray.length());

            // Pour chaque element du tableau
            for (int i = 0; i < jsonArray.length(); i++)
            {
                // Creation d'un tableau element a partir d'un JSONObject
                JSONObject jsonObj = jsonArray.getJSONObject(i);

                // Recuperation de l'item qui nous interesse
                if (jsonObj.getString("region").equals(filter)){
                    JSONObject translations = jsonObj.getJSONObject("translations");
                    JSONObject french = translations.getJSONObject("fra");
                    String nomPays = french.getString("common");
                    String capital = jsonObj.getString("capital");
                    String drapeau = jsonObj.getString("cca2").toLowerCase();
                    String continent = jsonObj.getString("region");
                    currentPays = new Pays(nomPays, capital, drapeau, continent);
                    // Ajout dans l'ArrayList
                    items.add(currentPays);
                }
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return items;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public String loadJSONFromAsset()
    {
        String json = null;
        try
        {
            InputStream is = this.getAssets().open("country.json");
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
        //Log.i("tag1", json);
        return json;
    }

}