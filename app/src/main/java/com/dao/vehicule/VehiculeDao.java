package com.dao.vehicule;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import com.dao.SQLiteHelper;
import com.example.locationvehicule.Vehicule;

import static com.dao.vehicule.IVehiculeContract.*;

/**
 * Created by bkrafft2016 on 23/10/2017.
 */
public class VehiculeDao {

    private static final String TAG = "VehiculeDAO" ;
    private SQLiteHelper sqLiteHelper;

    private SQLiteDatabase sqLiteDatabase;

    private String[] allColumns = {COLUMN_ID_VEHICULES, COLUMN_PRIX_VEHICULES,
            COLUMN_IMMATRICULATION_VEHICULES, COLUMN_TYPE_VEHICULES, COLUMN_MARQUE_VEHICULES,
            COLUMN_MODELE_VEHICULES, COLUMN_CARBURANT_VEHICULES,COLUMN_LOUE_VEHICULES};

    public VehiculeDao(Context context) {
        sqLiteHelper = new SQLiteHelper(context);
        sqLiteDatabase = sqLiteHelper.getWritableDatabase();
    }

    public long createVehicule(Vehicule vehicule) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_IMMATRICULATION_VEHICULES, vehicule.getImmatriculation());
        values.put(COLUMN_CARBURANT_VEHICULES, vehicule.getCarburant());
        values.put(COLUMN_MARQUE_VEHICULES, vehicule.getMarque().toUpperCase());
        values.put(COLUMN_MODELE_VEHICULES, vehicule.getModel());
        values.put(COLUMN_PRIX_VEHICULES, vehicule.getPrix());
        values.put(COLUMN_TYPE_VEHICULES, vehicule.getType());
        values.put(COLUMN_LOUE_VEHICULES, vehicule.getLoue());
        return sqLiteDatabase.insert(TABLE_VEHICULES, null, values);
    }

    public Cursor selectAllCursor() {
        return sqLiteDatabase.query(TABLE_VEHICULES, allColumns, null, null, null, null, null);
    }
    public String[] selectMarque(){
        String[] colMarque ={
                COLUMN_MARQUE_VEHICULES
        };

        String[] marques;
        Cursor cursor = sqLiteDatabase.query(true,TABLE_VEHICULES,colMarque,null,null,null,null,null,null);
        cursor.moveToFirst();
        marques = new String[cursor.getCount()];
        int i =0;
        while (!cursor.isAfterLast()) {
            marques[i] = cursor.getString(0);

            i++;
            cursor.moveToNext();
        }
        cursor.close();
        return marques;
    }
    public List<Vehicule> selectSearchVehicule(String marque,String carburant,int prix, int type, int dispo){
        List<Vehicule> vehicules = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query(TABLE_VEHICULES,allColumns," marque = '"+marque+"' AND carburant = '"+carburant+"' AND type ='"+type+"' AND "+" loue ='"+dispo+"' AND prix >= '"+prix+"'",null,null,null,null,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Vehicule vehicule = map(cursor);
            vehicules.add(vehicule);
            cursor.moveToNext();
        }
        cursor.close();
        return vehicules;
    }

    public ArrayList<Vehicule> selectAll() {
        ArrayList<Vehicule> vehicules = new ArrayList<>();
        Cursor c = selectAllCursor();
        while(c.moveToNext()) {
            vehicules.add(map(c));
        }

        return vehicules;
    }

    public ArrayList<Vehicule> selectAllRent(Boolean bool) {
        int boolzor = 0;
        if(bool){
            boolzor = 1;
        }
        ArrayList<Vehicule> vehicules = new ArrayList<>();
        Cursor c = sqLiteDatabase.query(TABLE_VEHICULES, allColumns, COLUMN_LOUE_VEHICULES + "= '" + boolzor +"'", null, null, null, null);
        while(c.moveToNext()) {
            vehicules.add(map(c));
        }

        return vehicules;
    }

    public Vehicule selectById(int id) {
        Vehicule vehicule = new Vehicule();
        Cursor c = sqLiteDatabase.query(TABLE_VEHICULES, null, COLUMN_ID_VEHICULES + "=" + id, null, null, null, null);
        c.moveToFirst();
        if(c.getCount() > 0){
            vehicule = map(c);
        }
        return vehicule;
    }

    public boolean update(Vehicule vehicule) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_IMMATRICULATION_VEHICULES, vehicule.getImmatriculation());
        values.put(COLUMN_CARBURANT_VEHICULES, vehicule.getCarburant());
        values.put(COLUMN_MARQUE_VEHICULES, vehicule.getMarque().toUpperCase());
        values.put(COLUMN_MODELE_VEHICULES, vehicule.getModel());
        values.put(COLUMN_PRIX_VEHICULES, vehicule.getPrix());
        values.put(COLUMN_TYPE_VEHICULES, vehicule.getType());
        if(vehicule.getLoue()){
            values.put(COLUMN_LOUE_VEHICULES, 1);
        }
         else{
            values.put(COLUMN_LOUE_VEHICULES, 0);
        }
        Boolean bool = sqLiteDatabase.update(TABLE_VEHICULES, values, COLUMN_ID_VEHICULES + " = " + vehicule.getId(), null) > 0;
        return bool;
    }

    public Vehicule map(Cursor c) {
        Vehicule vehicule = new Vehicule();
        vehicule.setPrix(c.getInt(NUM_COLUMN_PRIX_VEHICULES));
        vehicule.setImmatriculation(c.getString(NUM_COLUMN_IMMATRICULATION_VEHICULES));
        vehicule.setType(c.getInt(NUM_COLUMN_TYPE_VEHICULES));
        vehicule.setMarque(c.getString(NUM_COLUMN_MARQUE_VEHICULES));
        vehicule.setModel(c.getString(NUM_COLUMN_MODELE_VEHICULES));
        vehicule.setCarburant(c.getString(NUM_COLUMN_CARBURANT_VEHICULES));
        vehicule.setLoue(c.getInt(NUM_COLUMN_LOUE_VEHICULES)>0);

        vehicule.setId(c.getInt(NUM_COLUMN_ID_VEHICULES));
        Log.wtf(TAG, vehicule.toString());
        return vehicule;

    }
}
