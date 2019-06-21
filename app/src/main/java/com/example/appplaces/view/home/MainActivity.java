package com.example.appplaces.view.home;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.appplaces.R;
import com.example.appplaces.entity.Place;
import com.example.appplaces.entity.User;
import com.example.appplaces.presenter.MainPresenter;
import com.example.appplaces.view.place.PlaceActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MainInterface.View{
    private Button btnPlace;
    private MainInterface.Presenter presenter;
    private MaterialDialog dialog;
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private MainAdapter mainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnPlace = findViewById(R.id.btnPlace);
        recyclerView = (RecyclerView) findViewById(R.id.rvPlaces);
        gridLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        presenter = new MainPresenter(this);

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
                Intent intent = new Intent (v.getContext(), PlaceActivity.class);
                startActivityForResult(intent, 0);
            }
        });
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
        if (places.size() > 0 && places.get(0).getUser() != null){
            mainAdapter = new MainAdapter(places);
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
