package com.example.appplaces.view.home;

import com.example.appplaces.entity.Place;

import java.util.ArrayList;

public interface MainInterface {
    interface View{
        void showProgress();
        void hideProgress();

        void showPlaces(ArrayList<Place> places);

        void onError(String error);

    }
    interface Presenter{
        void onDestroy();
        void setPlaces();
    }
    interface Model{
        void getPlaces();
    }
    interface TaskListener{
        void onError(String error);
        void loadPlaces(ArrayList<Place> places);
    }
}
