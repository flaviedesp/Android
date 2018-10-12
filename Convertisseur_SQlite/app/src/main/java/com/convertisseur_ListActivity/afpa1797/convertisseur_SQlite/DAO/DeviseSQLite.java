package com.convertisseur_ListActivity.afpa1797.convertisseur_SQlite.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DeviseSQLite extends SQLiteOpenHelper {

    public static final String TABLE_DEVISES = "devises";
    public static final String COL_CODE_DEV = "code_dev";
    public static final String COL_DEV_FR = "dev_fr";
    public static final String COL_DEV_EN = "dev_en";
    public static final String COL_TAUX = "taux";

    public static final String CREATE_TABLE_DEVISES = "CREATE TABLE " + TABLE_DEVISES + "("
            + COL_CODE_DEV + " TEXT PRIMARY KEY," + COL_DEV_FR + " TEXT, "
            + COL_DEV_EN + " TEXT, " + COL_TAUX + " REAL);";

    public static final String DROP_TABLE_DEVISES = "DROP TABLE IF EXISTS " + TABLE_DEVISES + ";";

    public static final String INSERT_CHF = " INSERT INTO " + TABLE_DEVISES
            + "(" + COL_CODE_DEV + "," + COL_DEV_FR + "," + COL_DEV_EN + "," + COL_TAUX + ")"
            + " VALUES('CHF','CHF Franc Suisse(Suisse)','CHF Swiss Franc (Switzerland)', 0.958865);";

    public static final String INSERT_DKK = " INSERT INTO " + TABLE_DEVISES
            + "(" + COL_CODE_DEV + "," + COL_DEV_FR + "," + COL_DEV_EN + "," + COL_TAUX + ")"
            + " VALUES('DKK','DKK Couronne Danoise (Danemark)','DKK Danish Krone (Denmark)', 6.251655);";

    public static final String INSERT_DZD = " INSERT INTO " + TABLE_DEVISES
            + "(" + COL_CODE_DEV + "," + COL_DEV_FR + "," + COL_DEV_EN + "," + COL_TAUX + ")"
            + " VALUES('DZD','DZD Dinard (Algérie)','DZD Dinard (Algeria)', 111.009);";

    public static final String INSERT_EGP = " INSERT INTO " + TABLE_DEVISES
            + "(" + COL_CODE_DEV + "," + COL_DEV_FR + "," + COL_DEV_EN + "," + COL_TAUX + ")"
            + " VALUES('EGP','EGP Livre Egypte (Egypte)','EGP Livre Egypt (Egypt)', 17.6627);";

    public static final String INSERT_EUR = " INSERT INTO " + TABLE_DEVISES
            + "(" + COL_CODE_DEV + "," + COL_DEV_FR + "," + COL_DEV_EN + "," + COL_TAUX + ")"
            + " VALUES('EUR','EUR Euro (Union européenne)','EUR Euro (Union Européa)', 0.840503);";

    public static final String INSERT_GBP = " INSERT INTO " + TABLE_DEVISES
            + "(" + COL_CODE_DEV + "," + COL_DEV_FR + "," + COL_DEV_EN + "," + COL_TAUX + ")"
            + " VALUES('GBP','GBP Livre (Grande Bretagne)','GBP Livre (Britain)', 0.771731);";

    public static final String INSERT_JPY = " INSERT INTO " + TABLE_DEVISES
            + "(" + COL_CODE_DEV + "," + COL_DEV_FR + "," + COL_DEV_EN + "," + COL_TAUX + ")"
            + " VALUES('JPY','JPY Yen (Japon)','JPY Yen (Japan)', 109.300);";

    public static final String INSERT_MAD = " INSERT INTO " + TABLE_DEVISES
            + "(" + COL_CODE_DEV + "," + COL_DEV_FR + "," + COL_DEV_EN + "," + COL_TAUX + ")"
            + " VALUES('MAD','MAD Dirhan (Maroc)','MAD Dirhan (Morocco)',0.771731);";

    public static final String INSERT_RUB = " INSERT INTO " + TABLE_DEVISES
            + "(" + COL_CODE_DEV + "," + COL_DEV_FR + "," + COL_DEV_EN + "," + COL_TAUX + ")"
            + " VALUES('RUB','RUB Rouble russe (Russie)','RUB Rouble russia (Russia)',57.879025);";

    public static final String INSERT_USD = " INSERT INTO " + TABLE_DEVISES
            + "(" + COL_CODE_DEV + "," + COL_DEV_FR + "," + COL_DEV_EN + "," + COL_TAUX + ")"
            + " VALUES('USD','USD Dollar (Etats-Unis)','USD Dollar (USA)',1.0);";


    public DeviseSQLite( Context context,
                        String name,
                       SQLiteDatabase.CursorFactory factory,
                        int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("DeviseSQLite", "Passage dans le onCreate()");
        db.execSQL(CREATE_TABLE_DEVISES);
        //Peuplement de la base
        Log.i("DeviseSQLite", "Peuplement de la base");
        db.execSQL(INSERT_CHF);
        db.execSQL(INSERT_DKK);
        db.execSQL(INSERT_DZD);
        db.execSQL(INSERT_EGP);
        db.execSQL(INSERT_EUR);
        db.execSQL(INSERT_GBP);
        db.execSQL(INSERT_JPY);
        db.execSQL(INSERT_MAD);
        db.execSQL(INSERT_RUB);
        db.execSQL(INSERT_USD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("DeviseSQLite", "Passage dans le onUpgrade()");
        db.execSQL(DROP_TABLE_DEVISES);
        onCreate(db);
    }
}
