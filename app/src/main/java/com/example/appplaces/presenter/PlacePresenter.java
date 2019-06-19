package com.example.appplaces.presenter;

import com.example.appplaces.entity.Place;
import com.example.appplaces.model.PlaceModel;
import com.example.appplaces.view.place.PlaceInterface;

public class PlacePresenter implements PlaceInterface.Presenter, PlaceInterface.TaskListener {
    private PlaceInterface.View view;
    private PlaceInterface.Model model;

    public PlacePresenter(PlaceInterface.View view) {
        this.view = view;
        model = new PlaceModel(this);
    }

    @Override
    public void onDestroy() {
        view = null;
    }

    @Override
    public void toSave(Place place) {
        if (view != null) {
            view.disableInputs();
            view.showProgress();
        }
        model.doSave(place);
    }

    @Override
    public void onSuccess() {
        if (view != null){
            view.enableInputs();
            view.hideProgress();
            view.onSave();
        }
    }

    @Override
    public void onError(String error) {
        if (view != null){
            view.enableInputs();
            view.hideProgress();
            view.onError(error);
        }
    }
}
