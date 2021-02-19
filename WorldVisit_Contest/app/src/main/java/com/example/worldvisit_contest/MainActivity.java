package com.example.worldvisit_contest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ajouter_pays);
    }

    public void Back(View v) {
        //pour retourner a l’activite principale il suffit seulement d’appler la methode finish() qui vas tuer cette activite

        finish() ;

    }

}