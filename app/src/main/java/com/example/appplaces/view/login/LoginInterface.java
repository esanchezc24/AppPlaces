package com.example.appplaces.view.login;

public interface LoginInterface {
    interface View{
        void disableInputs();
        void enableInputs();

        void showProgress();
        void hideProgress();

        void handleLogin();

        boolean isValidEmail();
        boolean isValidPassword();

        void onLogin();
        void onError(String error);

    }
    interface Presenter{
        void onDestroy();
        void toLogin(String email, String password);
        boolean toSesion();
    }
    interface Model{
        boolean doSesion();
        void doLogin(String email, String password);
    }
    interface TaskListener{
        void onSuccess();
        void onError(String error);
    }
}
