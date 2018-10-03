package com.example.afpa1797.convertisseur_activity;

/**
 * Classe d'Exception pour les erreurs de saisie
 * @version Créée le 04/09/2017
 * @author Bonneau Didier
 * @see Exception
 */

public class SaisieException extends Exception {

    public static final String ERR_DEV_DEP = "Veuillez saisir une devise de départ";
    public static final String ERR_DEV_ARR = "Veuillez saisir une devise d'arrivée";
    public static final String ERR_DEV_MONT_VIDE = "Veuillez saisir un montant";
    public static final String ERR_DEV_MONT_NUM = "Saisissez un montant numérique !";

    public SaisieException(String messErreur) {
        super(messErreur);

    }

}


