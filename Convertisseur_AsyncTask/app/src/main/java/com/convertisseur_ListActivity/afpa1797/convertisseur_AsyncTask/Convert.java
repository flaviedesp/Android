package com.convertisseur_ListActivity.afpa1797.convertisseur_AsyncTask;

import android.content.Context;
import android.util.Log;

import com.convertisseur_ListActivity.afpa1797.convertisseur_AsyncTask.DAO.DeviseDAO;
import com.convertisseur_ListActivity.afpa1797.convertisseur_AsyncTask.Modele.Devise;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * Classe contenant la méthode static de conversion d'une devise source vers une devise cible
 * @version Créée le 19/05/2015. Mise à jour le 05/09/2017
 * @author Bonneau Didier
 */
public class Convert {

    private static Map<String, Double> conversionTable = new HashMap<String, Double>();

    // static fonctionne comme un constructeur dans une classe static
    /*static
    {
        conversionTable.put("CHF", Double.valueOf(0.958865));  // Franc Suisse (Suisse)
        conversionTable.put("DKK", Double.valueOf(6.251655));  // Couronne (Danemark)
        conversionTable.put("DZD", Double.valueOf(111.009));   // Dinar (Algérie)
        conversionTable.put("EGP", Double.valueOf(17.6627));   // Livre (Egypte)
        conversionTable.put("EUR", Double.valueOf(0.840503));  // Euro (Union Européenne)
        conversionTable.put("GBP", Double.valueOf(0.771731));  // Livre (Grande Bretagne)
        conversionTable.put("JPY", Double.valueOf(109.300));   // Yen (Japon)
        conversionTable.put("MAD", Double.valueOf(0.771731));  // Dirhan (Maroc)
        conversionTable.put("RUB", Double.valueOf(57.879025)); // Rouble russe (Russie)
        conversionTable.put("USD", Double.valueOf(1.0));       // Dollar (Etats Unis)
    }*/

    /**
     * Retourne un Double correspondant au <b>montant</b> en devise <b>source</b> converti en devise <b>cible</b>
     * @param source String : représentant la devise source
     * @param cible String : représentant la devise cible
     * @param montant Double : montant à convertir
     * @return Double : le montant converti en devise cible
     */
    public static double convertir(String source, String cible, String montant) {

        source = source.substring(0,3).toUpperCase();
        cible = cible.substring(0,3).toUpperCase();

        Log.d("MAIN", "conversion de " + source.substring(0,3) + " vers " + cible.substring(0,3));
        double tauxSource = conversionTable.get(source);
        double tauxCible = conversionTable.get(cible);
        double tauxConversion = tauxCible/tauxSource;

        return (Double.parseDouble(montant) * tauxConversion) ;
    }

    /**
     * Vérification des erreurs de saisie, si erreur : une exception est levée
     * @param source String : représentant la devise source
     * @param cible String : représentant la devise cible
     * @param montant Double : montant à convertir
     * @throws SaisieException
     */
    public static void validation(String source, String cible, String montant) throws SaisieException {

        source = source.substring(0,3).toUpperCase();
        cible = cible.substring(0,3).toUpperCase();

        if (!conversionTable.containsKey(source))
            throw new SaisieException(SaisieException.ERR_DEV_DEP);

        if (!conversionTable.containsKey(cible))
            throw new SaisieException(SaisieException.ERR_DEV_ARR);

        if (montant.equals("")) {
            throw new SaisieException(SaisieException.ERR_DEV_MONT_VIDE);
            // Vérification montant numérique
        }
        // vérif obsolète si champ de saisie défini en DecimalNumber
        try {
                Double.valueOf(montant);
            } catch (NumberFormatException nfe) {
                throw new SaisieException(SaisieException.ERR_DEV_MONT_NUM);
            }
    }

    /**
     * Retourne les libellés des devises en français présente dans la base de données
     * Rempli la Map convertionTable avec le code devise et son taux
     * @param contActivity le Context de l'application
     * @return String[] La liste libellés des devises en français
     */
    public static ArrayList<Devise> getDevisesSQlite(Context contActivity) {

        //String[] listeDevises = null; // = contActivity.getResources().getStringArray(R.array.liste_devises);

        ArrayList<Devise> devises = new ArrayList<Devise>();

        //Création d'une instance de ma classe DeviseDAO
        DeviseDAO deviseDao = new DeviseDAO(contActivity);

        //On ouvre la base de données pour écrire dedans
        deviseDao.open();

        //On récupère la liste des devises présentes dans la table DEVISES
        devises = deviseDao.getAll();

        Iterator itr = devises.iterator();

        //listeDevises = new String[devises.size()];

        //int i = 0;
        Devise dev;
        while(itr.hasNext()) {
            dev = (Devise)itr.next();
            conversionTable.put(dev.getCodeDev(), (double)dev.getTaux());
            //listeDevises[i++] = dev.getDevFR();
        }

        return devises;
    }

    public static ArrayList<Devise> getDevisesREST_GET(Context contActivity, String adrServeur, String req) throws IOException {

        HttpURLConnection conn = null;
        ArrayList<Devise> devises = new ArrayList<Devise>();

        InputStream inputStream = null;
        int len = 4096;
        Log.d("REST", "Entrée dans getDeviseREST_GET");
        try {
            //HttpClient httpClient = new DefaultHttpClient();
            //String url = adrServeur + "?" + req;
            //HttpGet httpGet = new HttpGet(url);
            //HttpResponse response = null;
            //response = httpClient.execute(httpGet);
            //HttpEntity entity = response.getEntity();
            //is = entity.getContent();

            URL url = new URL(adrServeur + "?" + req);
            Log.d("REST", "URL: " + url.toString());

            conn = (HttpURLConnection) url.openConnection();
            Log.d("REST", "openConnection()");

            conn.setReadTimeout(10000 /* milliseconds */);
            Log.d("REST", "setReadTimeout()");
            conn.setConnectTimeout(10000 /* milliseconds */);
            Log.d("REST", "setConnectTimeout()");
            //conn.setRequestMethod("GET");
            Log.d("REST", "setRequestMethod()");
            conn.connect();
            Log.d("REST", "connect()");
            // test du code retour de la requête http : juste pour info, non testeée dans la suite
            int codeReponse = conn.getResponseCode();
            Log.d("REST", "Code réponse de la connection: " + codeReponse);
            if (codeReponse != HttpURLConnection.HTTP_OK) {
                throw new IOException("HTTP error code: " + codeReponse);
            }

            // récupération de la réponse sous forme InputStream
            inputStream = conn.getInputStream();

            // Converssion de l'InputStream en une string
            String jsonString = readStream(inputStream, len);
            Log.d("REST", "String Json : " + jsonString);
            // { "devises": [{"monnaie":"Dirham","taux":"8.5656"},{"monnaie":"Dollars CA","taux":"1.1"}]}

            // utilisation de l'api Json : converssion en objet Json
            //JSONObject jsonObject = new JSONObject(jsonString);

            //Log.d("REST", jsonObject.toString());
            //JSONArray array = new JSONArray(jsonObject.getString("devises"));
            JSONArray array = new JSONArray(jsonString);

            Log.d("REST", "Longueur array json : " + array.length());

            Devise devise;
            for (int i = 0; i < array.length(); i++) {
                // On récupère un objet JSON du tableau
                JSONObject dev = new JSONObject(array.getString(i));
                // On fait le lien devise - Objet JSON
                devise = new Devise(dev.getString("code_dev"), dev.getString("dev_fr"), dev.getString("dev_en"), (float)dev.getDouble("taux"));
                Log.d("REST", "Devise : " + devise.toString());
                devises.add(devise);
            }

        } catch (JSONException e){
            Log.v("REST", "Erreur de JSON : " + e.getStackTrace());

        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (conn != null) {
                conn.disconnect();
            }

        }

        // remplissage de la Map conversionTable
        Iterator itr = devises.iterator();

        Devise dev;
        while(itr.hasNext()) {
            dev = (Devise)itr.next();
            conversionTable.put(dev.getCodeDev(), (double)dev.getTaux());
        }

        return devises;
    }


    // lit un InputStream et le convertit en String.
    private static String readStream(InputStream stream, int len) throws IOException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }

    public static ArrayList<String> getDevises() {
        ArrayList<String> devises = new ArrayList<String>();

        for (Iterator<String> it = conversionTable.keySet().iterator(); it.hasNext();){
            devises.add((String)it.next());
        }
        return devises;

    }

    /**
     * Accesseur du tableau associatif des devises
     * @return une référence sur la table des devises
     */
    public static Map<String, Double> getConversionTable() {
        return conversionTable;
    }

}
