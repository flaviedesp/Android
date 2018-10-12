package com.convertisseur_ListActivity.afpa1797.convertisseur_AsyncTask;

/**
 * Classe d'Exception pour les erreurs de saisie
 * @version Créée le 04/09/2017
 * @author Bonneau Didier
 * @see Exception
 */

public class SaisieException extends Exception {

    public static final String ERR_DEV_DEP = "msg_err_dev_dep"; //"Veuillez saisir une devise de départ";
    public static final String ERR_DEV_ARR = "msg_err_dev_arr"; //"Veuillez saisir une devise d'arrivée";
    public static final String ERR_DEV_MONT_VIDE = "msg_err_mont_vide"; //"Veuillez saisir un montant";
    public static final String ERR_DEV_MONT_NUM = "msg_err_mont_num"; //"Saisissez un montant numérique !";

    public SaisieException(String messErreur) {
        super(messErreur);

    }

}


