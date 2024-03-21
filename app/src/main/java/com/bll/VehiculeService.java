package com.bll;

import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;

import com.dao.vehicule.VehiculeDao;
import com.example.locationvehicule.Vehicule;

/**
 * Created by Administrators on 24/03/2024.
 */
public class VehiculeService {

    public interface VehiculeServiceListener {
        void OnSelectVehicule(ArrayList<Vehicule> vehicules);
    }

    public static void selectAll(final VehiculeServiceListener listener, final Context context) {
        new AsyncTask<Void, Void, ArrayList<Vehicule>>() {
            @Override
            protected ArrayList<Vehicule> doInBackground(Void... voids) {
                return new VehiculeDao(context).selectAll();
            }

            @Override
            protected void onPostExecute(ArrayList<Vehicule> vehicules) {
                super.onPostExecute(vehicules);
                listener.OnSelectVehicule(vehicules);
            }
        }.execute();
    }

    public static void selectAllRent(final VehiculeServiceListener listener, final Context context, final Boolean bool) {
        new AsyncTask<Void, Void, ArrayList<Vehicule>>() {
            @Override
            protected ArrayList<Vehicule> doInBackground(Void... voids) {
                return new VehiculeDao(context).selectAllRent(bool);
            }

            @Override
            protected void onPostExecute(ArrayList<Vehicule> vehicules) {
                super.onPostExecute(vehicules);
                listener.OnSelectVehicule(vehicules);
            }
        }.execute();
    }
}
