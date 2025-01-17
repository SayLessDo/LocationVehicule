package com.example.locationvehicule;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import com.dao.edl.EDLDao;
import com.dao.photo.PhotoDao;
import com.models.EDL;
import com.models.LocationVehicule;
import com.models.Photo;

public class ListeEtatDesLieux extends AppCompatActivity {
    private LocationVehicule location;
    private EDLDao edlDao;
    private TextView dateDepart;
    private TextView dateRetour;
    private PhotoDao photoDao;
    private List<Photo> photos;
    private List<EDL> edls;
    private RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_etat_des_lieux);
        Intent intent = getIntent();
        if(intent.hasExtra("location")){
            location = intent.getExtras().getParcelable("location");
        }
        edlDao = new EDLDao(this);
        edls = edlDao.getEdlByLocation(location.getId());

        photoDao = new PhotoDao(this);
        photos = photoDao.selectPhotoByEdl(edls.get(0).getId());
        dateDepart = (TextView) findViewById(R.id.txt_date_depart);
        dateRetour = (TextView) findViewById(R.id.txt_date_retour);
        dateDepart.setText(dateDepart.getText().toString()+ " " +location.getDepart().toString());
        if(location.getRetour() !=null){
            dateRetour.setText(dateRetour.getText().toString()+" "+location.getRetour().toString());
        }

        relativeLayout = (RelativeLayout) findViewById(R.id.rl_liste_edl);

        for(int x=0;x<photos.size();x++) {
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

            bmOptions.inSampleSize = 1;
            ImageView image = new ImageView(this);
            image.setId(x);
            relativeLayout.addView(image);
            Bitmap bitmap = BitmapFactory.decodeFile(photos.get(x).getUri(),bmOptions);
            image.setImageBitmap(bitmap);
            layoutParams.width = 250;
            layoutParams.height = 250;
            if(x==1){
                layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, (x));
                layoutParams.addRule(RelativeLayout.BELOW, R.id.txt_date_retour);

            }else if(x==2){
                layoutParams.addRule(RelativeLayout.BELOW, R.id.txt_date_retour);
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, (x-1));
            }
            else{
                layoutParams.addRule(RelativeLayout.BELOW, R.id.txt_date_retour);

            }
            image.setLayoutParams(layoutParams);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar, menu);
        return true;
    }

    public void showAddCar(MenuItem item) {
        Intent intent = new Intent(ListeEtatDesLieux.this, ManageVehicule.class);
        startActivity(intent);
    }
    public void showCarsList(MenuItem item) {
        Intent intent = new Intent(ListeEtatDesLieux.this, ListeVehiculeActivity.class);
        startActivity(intent);
    }

    public void showVehiculeSearch(MenuItem item) {
        Intent intent = new Intent(ListeEtatDesLieux.this, SearchVehicule.class);
        startActivity(intent);
    }

    public void showStats(MenuItem item) {
        Intent intent = new Intent(ListeEtatDesLieux.this, StatsActivity.class);
        startActivity(intent);
    }
}
