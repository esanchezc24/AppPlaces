package com.example.appplaces.presenter;

import com.example.appplaces.entity.Place;
import com.example.appplaces.model.MainModel;
import com.example.appplaces.view.home.MainInterface;

import java.util.ArrayList;

public class MainPresenter implements MainInterface.Presenter, MainInterface.TaskListener {
    private MainInterface.View view;
    private MainInterface.Model model;

    public MainPresenter(MainInterface.View view) {
        this.view = view;
        model = new MainModel(this);
    }

    @Override
    public void onDestroy() {
        view = null;
    }

    @Override
    public void setPlaces() {
        if (view !=null){
            view.showProgress();
            model.getPlaces();
        }

    }

    @Override
    public void onError(String error) {
        if (view !=null){
            view.onError(error);
        }
    }

    @Override
    public void loadPlaces(ArrayList<Place> places) {
        if (view !=null){
            view.showPlaces(places);
        }

    }
}
