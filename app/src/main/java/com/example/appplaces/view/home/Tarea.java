package com.example.appplaces.view.home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;

import com.example.appplaces.model.MainModel;

class Tarea extends BroadcastReceiver implements MainInterface.Location {
    private MainInterface.Model model = new MainModel();
    private LocationManager locManager;
    private Location location;

    public Tarea() {

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String latitud = intent.getStringExtra("latitud");
        String longitud = intent.getStringExtra("longitud");
        setLocation(latitud,longitud);
    }


    @Override
    public void setLocation(String latitud, String longitud) {
        model.saveLocation(latitud,longitud);
    }
}
