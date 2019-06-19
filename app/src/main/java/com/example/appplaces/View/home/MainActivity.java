package com.example.appplaces.View.home;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.appplaces.R;
import com.example.appplaces.View.place.PlaceActivity;

public class MainActivity extends AppCompatActivity {
    private Button btnPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnPlace = findViewById(R.id.btnPlace);
        btnPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), PlaceActivity.class);
                startActivityForResult(intent, 0);
            }
        });
    }
}
