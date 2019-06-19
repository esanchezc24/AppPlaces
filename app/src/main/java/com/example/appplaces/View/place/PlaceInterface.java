package com.example.appplaces.View.place;

import com.example.appplaces.Data.Entity.Place;

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
        void toSave(Place place);
    }
    interface Model{
        void doSave(Place place);
    }
    interface TaskListener{
        void onSuccess();
        void onError(String error);
    }
}
