package com.example.appplaces.view.home;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.afollestad.materialdialogs.MaterialDialog;
import com.example.appplaces.R;
import com.example.appplaces.entity.Place;
import com.example.appplaces.presenter.MainPresenter;
import com.example.appplaces.view.place.PlaceActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MainInterface.View {
    private Button btnPlace;
    private MainInterface.Presenter presenter;
    private MaterialDialog dialog;
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private MainAdapter mainAdapter;
    private LocationManager locationManager;
    AlarmManager alarmManager;
    String latitud, longitud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        btnPlace = findViewById(R.id.btnPlace);
        recyclerView = (RecyclerView) findViewById(R.id.rvPlaces);
        gridLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        presenter = new MainPresenter(this);
        location();

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(this, Tarea.class);
        intent.putExtra("latitud", latitud);
        intent.putExtra("longitud", longitud);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        // Media hora 1800000
        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, 0, 1800000, pendingIntent);

        MaterialDialog.Builder builder = new MaterialDialog.Builder(this)
                .title("Cargando")
                .content("Espere, por favor...")
                .progress(true, 0)
                .cancelable(false);
        dialog = builder.build();
        presenter.setPlaces();

        btnPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), PlaceActivity.class);
                startActivityForResult(intent, 0);
            }
        });
    }

    private void location() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1000);
        }
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (locationManager != null) {
            latitud = String.valueOf(location.getLatitude());
            longitud = String.valueOf(location.getLongitude());
        }

    }

    @Override
    public void showProgress() {
        dialog.show();
    }

    @Override
    public void hideProgress() {
        dialog.dismiss();
    }

    @Override
    public void showPlaces(ArrayList<Place> places) {
        if (places.size() > 0 && places.get(0).getUser() != null) {
            mainAdapter = new MainAdapter(places, this);
            recyclerView.setAdapter(mainAdapter);
        }
    }

    @Override
    public void onError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }


}
