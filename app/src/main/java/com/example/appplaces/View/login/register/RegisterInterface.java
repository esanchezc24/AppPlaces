package com.example.appplaces.View.login.register;

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
        void toRegister(String name, String email, String password);
    }
    interface Model{
        void doRegister(String name,String email, String password);
    }

    interface TaskListener{
        void onSuccess();
        void onError(String error);
    }
}
