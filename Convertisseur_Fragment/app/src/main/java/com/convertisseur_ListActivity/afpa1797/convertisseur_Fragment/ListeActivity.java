package com.convertisseur_ListActivity.afpa1797.convertisseur_Fragment;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.convertisseur_ListActivity.afpa1797.convertisseur_Fragment.DAO.DeviseREST;

import java.util.ArrayList;

public class ListeActivity extends ListActivity {

    private ArrayList<String> devises;
    private ArrayAdapter<String> adapter;

    public static final String URL = "http://10.75.96.104:81/";
    public static final String REQ = "req=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste);

        //devises = Convert.getDevises();
        devises = new ArrayList<String>();

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, devises);

        setListAdapter(adapter);
        DeviseREST maTacheAsynchrone = new DeviseREST(this, adapter);
        maTacheAsynchrone.execute(URL, REQ);
    }
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        String codeDevise = ((String)getListAdapter().getItem(position)).substring(0,3).toUpperCase();

        Double taux = Convert.getConversionTable().get(codeDevise);

        Toast.makeText(this,codeDevise + " : Taux = " + taux , Toast.LENGTH_SHORT).show();
    }
}