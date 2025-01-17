package com.example.locationvehicule;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import com.adapter.VehiculeAdapter;
import com.bll.VehiculeService;

public class ListeVehiculeActivity extends AppCompatActivity implements VehiculeService.VehiculeServiceListener{

    private ArrayList<Vehicule> vehicules;
    private VehiculeAdapter adapter;
    ListView listView;

    RadioButton rb_louees;
    RadioButton rb_disponibles;
    RadioButton rb_toutes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_vehicule);

        vehicules = new ArrayList<>();
        adapter = new VehiculeAdapter(this, R.layout.presentation_liste_vehicule, vehicules);
        listView = (ListView) findViewById(R.id.list_vehicules);
        listView.setAdapter(adapter);

        rb_louees = (RadioButton)findViewById(R.id.rb_loue);
        rb_disponibles = (RadioButton)findViewById(R.id.rb_disponible);
        rb_toutes = (RadioButton)findViewById(R.id.rb_toutes);

        rb_toutes.setChecked(true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ListeVehiculeActivity.this, DetailVehiculeActivity.class);
                Vehicule vehicule = adapter.getItem(i);
                intent.putExtra("vehicule", vehicule);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        VehiculeService.selectAll(this, this);
    }

    @Override
    public void OnSelectVehicule(ArrayList<Vehicule> vehicules) {
        this.vehicules.clear();
        this.vehicules.addAll(vehicules);
        adapter.notifyDataSetChanged();
    }

    public void onClickRefreshListeVehicules(View view) {
        if(rb_louees.isChecked()){
            VehiculeService.selectAllRent(this, this, true);
        }
        else if(rb_disponibles.isChecked()){
            VehiculeService.selectAllRent(this, this, false);
        }
        else if(rb_toutes.isChecked()){
            VehiculeService.selectAll(this,this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar, menu);
        return true;
    }

    public void showAddCar(MenuItem item) {
        Intent intent = new Intent(ListeVehiculeActivity.this, ManageVehicule.class);
        startActivity(intent);
    }
    public void showCarsList(MenuItem item) {
        Intent intent = new Intent(ListeVehiculeActivity.this, ListeVehiculeActivity.class);
        startActivity(intent);
    }

    public void showVehiculeSearch(MenuItem item) {
        Intent intent = new Intent(ListeVehiculeActivity.this, SearchVehicule.class);
        startActivity(intent);
    }

    public void showStats(MenuItem item) {
        Intent intent = new Intent(ListeVehiculeActivity.this, StatsActivity.class);
        startActivity(intent);
    }
}
