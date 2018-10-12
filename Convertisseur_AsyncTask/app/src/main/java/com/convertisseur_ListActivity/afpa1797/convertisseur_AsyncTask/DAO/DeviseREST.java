package com.convertisseur_ListActivity.afpa1797.convertisseur_AsyncTask.DAO;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import com.convertisseur_ListActivity.afpa1797.convertisseur_AsyncTask.Convert;
import com.convertisseur_ListActivity.afpa1797.convertisseur_AsyncTask.Modele.Devise;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;


public class DeviseREST extends AsyncTask<String,Void,ArrayList<Devise>> {

    private Context context;
    private ArrayAdapter<String> adapter;
    private ProgressDialog progressDialog;


    public DeviseREST(Context context, ArrayAdapter<String> adapter) {
        super();
        this.context = context;
        this.adapter = adapter;
        Log.v("TraitSync","Constructeur MainAsyncTasck");
    }

    @Override
    protected ArrayList<Devise> doInBackground(String... params) {
        ArrayList<Devise> listeDevises = null;

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if(networkInfo !=null && networkInfo.isConnected()){
            Log.d("TraitSync", "internet ok");
            try {
                listeDevises = Convert.getDevisesREST_GET(context, params[0], params[1]);
            } catch (Exception e) {
                //e.printStackTrace();
                listeDevises = Convert.getDevisesSQlite(context);
                Log.d("TraitSync", e.getMessage());
            }
        }else{
            Log.d("TraitSync", "internet non ok");
            listeDevises = Convert.getDevisesSQlite(context);
        }
        return listeDevises;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        Log.v("TraitSync","OnPreExecute");
        /*this.progressDialog = new ProgressDialog(context);
        this.progressDialog.setMessage("Début du traitement asynchrone");
        this.progressDialog.setIndeterminate(true);
        this.progressDialog.setCancelable(true);
        this.progressDialog.show();*/
        progressDialog = ProgressDialog.show(context,
                "Convertisseur_AsyncTask", "Début du traitement asynchrone, \npatientez....",
                true, true);
    }

    @Override
    protected void onPostExecute(ArrayList<Devise> devises) {
        super.onPostExecute(devises);
        Iterator itr = devises.iterator();
        ArrayList<String> libelles = new ArrayList<String>();

        while(itr.hasNext()){
            String lib = ((Devise)itr.next()).getDevFR();
            libelles.add(lib);
        }
        adapter.addAll(libelles);
        progressDialog.dismiss();
    }
}
