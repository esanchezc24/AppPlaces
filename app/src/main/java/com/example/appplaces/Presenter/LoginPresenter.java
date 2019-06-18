package com.example.appplaces.Presenter;

import com.example.appplaces.Model.LoginModel;
import com.example.appplaces.View.login.LoginInterface;

public class LoginPresenter implements LoginInterface.Presenter, LoginInterface.TaskListener {
    private LoginInterface.View view;
    private LoginInterface.Model model;

    public LoginPresenter(LoginInterface.View view) {
        this.view = view;
        model = new LoginModel(this);
    }

    @Override
    public void onDestroy() {
        view = null;
    }

    @Override
    public void toLogin(String email, String password) {
        if (view != null) {
            view.disableInputs();
            view.showProgress();
        }
        model.doLogin(email, password);
    }

    @Override
    public void onSuccess() {
        if (view != null){
            view.enableInputs();
            view.hideProgress();
            view.onLogin();
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
