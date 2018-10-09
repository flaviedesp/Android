package com.convertisseur_ListActivity.afpa1797.convertisseur_ListActivity;

import android.content.Context;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Classe contenant la méthode static de conversion d'une devise source vers une devise cible
 * @version Créée le 19/05/2015. Mise à jour le 05/09/2017
 * @author Bonneau Didier
 */
public class Convert {

    private static Map<String, Double> conversionTable = new HashMap<String, Double>();

    // static fonctionne comme un constructeur dans une classe static
    static
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
    }

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
     * Retourne la liste des devises à partir de la ressource String_Array liste_devises
     * @param contActivity le Context de l'application
     * @return String[] La liste de devises contenue dans la ressource String_Array Liste_devises
     */
    public static String[] getDevises(Context contActivity) {

        String[] listeDevises = contActivity.getResources().getStringArray(R.array.listeDevises);

        return listeDevises;
    }


    /**
     * Accesseur du tableau associatif des devises
     * @return une référence sur la table des devises
     */
    public static Map<String, Double> getConversionTable() {
        return conversionTable;
    }

}
