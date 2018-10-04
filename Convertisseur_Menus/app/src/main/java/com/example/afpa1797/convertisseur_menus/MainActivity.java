package com.example.afpa1797.convertisseur_menus;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /*@Override
    protected void onDestroy() {
        super.onDestroy();
        toast("Fin de l'application");
    }*/

    /**
     * Méthode appelée sur l'évènement OnClick du bouton "buttonConv"
     *
     * @param btn  le composant graphique Button (de classe View) cliqué
     */
    public void onClickConvertir(View btn) {
        // attention la méthode doConversion peut lever une exception de type SaisieException
        try {
            toast("Montant : " + doConversion());
        } catch (SaisieException se) {
            //récupération du code erreur
            String nomRessource = se.getMessage();
            //récupération de id de la ressource
            int id = getResources().getIdentifier(nomRessource,"string", "com.example.afpa1797.convertisseur_menus");
            Log.d("MAIN", "Id ressource = " + id);
            Log.d("MAIN", "valeur = " + getResources().getString(id) );
            //message de r&cuperation de la ressource
            toast(getResources().getString(id));
        }
    }

    /**
     * Méthode appelée sur l'évènement OnClick du bouton "buttonQuit"
     *
     * @param btn  le composant graphique Button (de classe View) cliqué
     */
    public void onClickQuitter(View btn) {
        toast("Fin de l'application");
        finish();
    }

    /**
     * Méthode appelée par la méthode onClickConvertir()
     *
     * @return Double : valeur de la conversion
     * @exception SaisieException erreur de saisie
     */
    private double doConversion() throws SaisieException {

        // récupération de la devise de départ à partir de l'EditText "editTextDevDep"
        String devDep = ((EditText)findViewById(R.id.code_depart)).getText().toString();
        // récupération de la devise d'arrivée à partir de l'EditText "editTextDevArr"
        String devArr = ((EditText)findViewById(R.id.code_arrive)).getText().toString();
        // récupération du montant à convertir à partir de l'EditText "editTextMont"
        String montant = ((EditText)findViewById(R.id.montant_affiche)).getText().toString();

        Log.d("MAIN_Conv", "le montant " + montant);

        // validation de la saisie : peut lever une SaisieException
        Convert.validation(devDep, devArr, montant);

        // appel de la méthode static convertir de la classe Convert
        return Convert.convertir(devDep, devArr, montant);

    }

    /**
     * Méthode d'affichage d'un Toast
     *
     * @param message : String du message à afficher par un Toast
     */
    public void toast(String message) {
        //Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.idApropos:
                return true;
            case R.id.idlangue:
                return true;
            case R.id.iddate:
                return true;
            case R.id.idaffichage:
                return true;
            case R.id.idQuitter:
                onClickQuitter(null);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
