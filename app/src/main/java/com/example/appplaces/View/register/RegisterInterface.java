package com.example.appplaces.View.register;

import com.example.appplaces.Data.Entity.User;

public interface RegisterInterface {
    interface View{
        void disableInputs();
        void enableInputs();

        void showProgress();
        void hideProgress();

        void handleRegister();

        boolean isValidName();
        boolean isValidEmail();
        boolean isValidPassword();

        void onRegister();
        void onError(String error);
    }
    interface Presenter{
        void onDestroy();
        void toRegister(User user);
    }
    interface Model{
        void doRegister(User user);
    }

    interface TaskListener{
        void onSuccess();
        void onError(String error);
    }
}
