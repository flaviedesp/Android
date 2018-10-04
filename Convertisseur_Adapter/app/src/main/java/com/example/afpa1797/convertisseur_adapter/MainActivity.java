package com.example.afpa1797.convertisseur_adapter;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

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
           doConversion();

        } catch (SaisieException se) {
            //récupération du code erreur
            String nomRessource = se.getMessage();
            //récupération de id de la ressource
            int id = getResources().getIdentifier(nomRessource,"string", "com.example.afpa1797.convertisseur_adapter");
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

        Intent monIntent = new Intent(getApplicationContext(), ResultActivity.class);

        monIntent.putExtra("dev_dep", devDep);
        monIntent.putExtra("dev_arr", devArr);
        monIntent.putExtra("mont", montant);

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
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        double resultat1 = data.getDoubleExtra("resultat1", 0.0);
        String resultat2 = data.getExtras().getString("resultat2");
        toast(resultat1 + " " + resultat2);

    }
}
