package com.convertisseur_ListActivity.afpa1797.convertisseur_SQlite.Modele;

public class Devise {
    private String codeDev;
    private String devFR;
    private String devEN;
    private float taux;

    public Devise(){
    }

    public Devise(String codeDev, String devFR, String devEN, float taux){
        this.codeDev = codeDev;
        this.devFR = devFR;
        this.devEN = devEN;
        this.taux = taux;
    }

    public String getCodeDev(){
        return codeDev;
    }

    public void setCodeDev(String codeDev){
        this.codeDev = codeDev;
    }

    public String getDevFR(){
        return devFR;
    }

    public void setDevFR(String devFR) {
        this.devFR = devFR;
    }

    public String getDevEN(){
        return devEN;
    }

    public void setDevEN(String devEN) {
        this.devEN = devEN;
    }

    public float getTaux() {
        return taux;
    }

    public void setTaux(float taux) {
        this.taux = taux;
    }
}
