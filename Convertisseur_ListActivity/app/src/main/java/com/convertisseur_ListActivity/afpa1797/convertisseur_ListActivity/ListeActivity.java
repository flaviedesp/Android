package com.convertisseur_ListActivity.afpa1797.convertisseur_ListActivity;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ListeActivity extends ListActivity {

    private String[] devises;
    private ArrayAdapter<String> adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste);

        devises = Convert.getDevises(this);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, devises);

        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

            String codeDevise = ((String)getListAdapter().getItem(position)).substring(0,3).toUpperCase();

            Double taux = Convert.getConversionTable().get(codeDevise);

            Toast.makeText(this,codeDevise + " : Taux = " + taux , Toast.LENGTH_SHORT).show();
    }
}