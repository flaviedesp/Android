package com.example.afpa1797.convertisseur_activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void doConvertion(View btn){
        double calcul = 0;

        String deviseDepart = ((EditText)findViewById(R.id.code_depart)).getText().toString();
        String deviseArrive = ((EditText)findViewById(R.id.code_arrive)).getText().toString();
        String textMontant = ((EditText)findViewById(R.id.montant_affiche)).getText().toString();

        Context context = getApplicationContext();
        int duration = Toast.LENGTH_LONG;

        try{
            Convert.validation(deviseDepart, deviseArrive,textMontant);
            calcul = Convert.convertir(deviseDepart, deviseArrive,textMontant);
            String text = String.valueOf(calcul);

            Toast.makeText(context, text, duration).show();

        } catch (SaisieException e){
            Toast.makeText(context, e.getMessage(), duration).show();
        }
            }
    public void Quitter(View btn){

        Context context = getApplicationContext();
        CharSequence text = "au revoir";
        int duration = Toast.LENGTH_SHORT;
        Toast.makeText(context, text, duration).show();

        finish();
    }

}
