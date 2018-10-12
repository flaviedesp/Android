package com.convertisseur_ListActivity.afpa1797.convertisseur_AsyncTask;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.convertisseur_ListActivity.afpa1797.convertisseur_AsyncTask.DAO.DeviseREST;
import com.convertisseur_ListActivity.afpa1797.convertisseur_AsyncTask.Modele.Devise;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> devises = null;
    private ArrayAdapter<String> adapter = null;
    private String monnaieDep;
    private String monnaieArr;
    private String montantEnr;

    public static final String URL = "http://10.75.96.104:81/";
    public static final String REQ = "req=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // devises = Convert.getDevises();
        devises = new ArrayList<String>();

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, devises);

        SharedPreferences mesPrefs = getSharedPreferences("preferences", Context.MODE_PRIVATE);

        monnaieDep = mesPrefs.getString("monnaieDep", "");
        monnaieArr = mesPrefs.getString("monnaieArr", "");

        chargerSpinner(R.id.spinner_dep, monnaieDep);
        chargerSpinner(R.id.spinner_arr, monnaieArr);

       //Spinner spinnerDep = ((Spinner)findViewById(R.id.spinner_dep));
       //Spinner spinnerArr = ((Spinner)findViewById(R.id.spinner_arr));

       //spinnerDep.setSelection(posDep);
       //spinnerArr.setSelection(posArr);

       EditText editMontant = (EditText)findViewById(R.id.montant_affiche);
       editMontant.setText(mesPrefs.getString("montantEnr", ""));

        DeviseREST maTacheAsynchrone = new DeviseREST(this, adapter);
        maTacheAsynchrone.execute(URL, REQ);
    }

    public void chargerSpinner(int idView, String devDefault){

        final Spinner spinner = (Spinner)findViewById(idView);

        spinner.setAdapter(adapter);

        int intDevDefault = adapter.getPosition(devDefault);

        //if (intDevDefault < 0 || intDevDefault >= spinner.getCount() ) intDevDefault = 0;

        spinner.setSelection(intDevDefault);
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
           doConversion();

        } catch (SaisieException se) {
            //récupération du code erreur
            String nomRessource = se.getMessage();
            //récupération de id de la ressource
            int id = getResources().getIdentifier(nomRessource,"string", "com.example.afpa1797.convertisseur_AsyncTask");
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
    private void doConversion() throws SaisieException {

        // récupération de la devise de départ à partir du Spinner
        String devDep = ((Spinner)findViewById(R.id.spinner_dep)).getSelectedItem().toString();
        // récupération de la devise d'arrivée à partir du Spinner
        String devArr = ((Spinner)findViewById(R.id.spinner_arr)).getSelectedItem().toString();
        // récupération du montant à convertir à partir de l'EditText "editTextMont"
        String montant = ((EditText)findViewById(R.id.montant_affiche)).getText().toString();

        Log.d("MAIN_Conv", "le montant " + montant);

        // validation de la saisie : peut lever une SaisieException
        Convert.validation(devDep, devArr, montant);

        this.monnaieDep = devDep;
        this.monnaieArr = devArr;
        this.montantEnr = montant;

        Intent monIntent = new Intent(getApplicationContext(), ResultActivity.class);

        monIntent.putExtra("dev_dep", monnaieDep);
        monIntent.putExtra("dev_arr", monnaieArr);
        monIntent.putExtra("mont", montantEnr);

        startActivityForResult(monIntent, 1);
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
                Intent nomInt = new Intent(getApplicationContext(), AProposActivity.class);
                startActivity(nomInt);
                return true;
            case R.id.idlangue:
                startActivity(new Intent(Settings.ACTION_LOCALE_SETTINGS));
                return true;
            case R.id.iddate:
                startActivity(new Intent(Settings.ACTION_DATE_SETTINGS));
                return true;
            case R.id.idaffichage:
                startActivity(new Intent(Settings.ACTION_DISPLAY_SETTINGS));
                return true;
            case R.id.idQuitter:
                onClickQuitter(null);
                return true;
            case R.id.idListeDevise:
                Intent monInt =  new Intent(getApplicationContext(), ListeActivity.class);
                startActivity(monInt);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("MAIN", "valeur du requestCode : " + requestCode);
        Log.d("MAIN", "valeur du code retour : " + resultCode);
        if (requestCode == 1){
            if(resultCode == RESULT_OK){
                toast("le résultat : " + data.getDoubleExtra("resultat", 0.0));
            }
        }
       /* double resultat1 = data.getDoubleExtra("resultat1", 0.0);
        String resultat2 = data.getExtras().getString("resultat2");
        toast(resultat1 + " " + resultat2);*/
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences mesPreferences = getSharedPreferences("preferences", MODE_PRIVATE);

        SharedPreferences.Editor editeur = mesPreferences.edit();
        editeur.putString("monnaieDep",monnaieDep);
        editeur.putString("monnaieArr", monnaieArr);
        editeur.putString("montantEnr", montantEnr);
        editeur.commit();
    }
}
