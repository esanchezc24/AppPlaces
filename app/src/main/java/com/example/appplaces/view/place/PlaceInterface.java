package com.example.appplaces.view.place;

import android.net.Uri;

import com.example.appplaces.entity.Place;

import java.util.ArrayList;

public interface PlaceInterface {
    interface View{
        void disableInputs();
        void enableInputs();

        void showProgress();
        void hideProgress();

        void handleSave();

        boolean isValidDescription();
        boolean isValidFotos();

        void onSave();
        void onError(String error);
    }
    interface Presenter{
        void onDestroy();
        void toSave(Place place, ArrayList<Uri> fotos);
    }
    interface Model{
        void doSave(Place place, ArrayList<Uri> fotos);
    }
    interface TaskListener{
        void onSuccess();
        void onError(String error);
    }
}
