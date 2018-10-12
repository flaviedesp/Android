package com.convertisseur_ListActivity.afpa1797.convertisseur_Fragment.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.convertisseur_ListActivity.afpa1797.convertisseur_Fragment.Modele.Devise;

import java.util.ArrayList;

public class DeviseDAO {
    public static final int VERSION_BDD = 2;
    public static final String NOM_BDD = "conv_devises.db";

    private SQLiteDatabase bdd;
    private DeviseSQLite maBaseSQLite;

    public DeviseDAO(Context context){
        maBaseSQLite = new DeviseSQLite(context, NOM_BDD, null, VERSION_BDD);
    }

    public void open(){
        bdd = maBaseSQLite.getWritableDatabase();
    }

    public void close(){
        bdd.close();
    }

    public ArrayList<Devise> getAll(){

        String sql = "SELECT * FROM " + DeviseSQLite.TABLE_DEVISES + " ORDER BY " + DeviseSQLite.COL_CODE_DEV;

        ArrayList<Devise> devises = new ArrayList<Devise>();

        Cursor c = bdd.rawQuery(sql, null);

        if(c != null){
            if(c.moveToFirst()){
                do{
                    String codeDev = c.getString(c.getColumnIndex(DeviseSQLite.COL_CODE_DEV));
                    String devFR = c.getString(c.getColumnIndex(DeviseSQLite.COL_DEV_FR));
                    String devEN = c.getString(c.getColumnIndex(DeviseSQLite.COL_DEV_EN));
                    float taux = c.getFloat(c.getColumnIndex(DeviseSQLite.COL_TAUX));
                    Devise dev = new Devise(codeDev, devFR, devEN, taux);
                    devises.add(dev);
                }while(c.moveToNext());
            }
        }
        return devises;
    }

}
