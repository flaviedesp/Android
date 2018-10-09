package com.example.afpa1797.convertisseur_adapter2;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class ResultActivity extends AppCompatActivity {

    double montant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent thisIntent = getIntent();
        String devdep1 = thisIntent.getExtras().getString("dev_dep");
        String devarr1 = thisIntent.getExtras().getString("dev_arr");
        String montant1 = thisIntent.getExtras().getString("mont");


        NumberFormat valeur = NumberFormat.getInstance();
        valeur.setMaximumFractionDigits(2);

        montant = Convert.convertir(devdep1, devarr1, montant1);

        String mont = valeur.format(montant);

        TextView devise1 = (TextView) findViewById(R.id.id_devise_dep);
        devise1.setText(montant1 + " " + devdep1);

        TextView result = (TextView) findViewById(R.id.id_resultat);
        result.setText(mont + " " + devarr1);

        /*thisIntent.putExtra("resultat", montant);
        thisIntent.putExtra("resultat2", devarr1);*/

        setResult(Activity.RESULT_OK, thisIntent);

    }
    public void onClickQuitter(View btn) {
        Intent intentRetour = new Intent(getApplicationContext(), MainActivity.class);
        intentRetour.putExtra("resultat", montant);
        setResult(RESULT_OK, intentRetour);
        finish();
    }
    public void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
