package com.example.appplaces.Presenter;

import com.example.appplaces.Model.RegisterModel;
import com.example.appplaces.View.login.register.RegisterInterface;

public class RegisterPresenter implements RegisterInterface.Presenter, RegisterInterface.TaskListener {
    private RegisterInterface.View view;
    private RegisterInterface.Model model;

    public RegisterPresenter(RegisterInterface.View view) {
        this.view = view;
        model = new RegisterModel(this);
    }

    @Override
    public void onDestroy() {
        view = null;
    }

    @Override
    public void toRegister(String name, String email, String password) {
        if (view != null) {
            view.disableInputs();
            view.showProgress();
        }
        model.doRegister(name, email, password);
    }

    @Override
    public void onSuccess() {
        if (view != null){
            view.enableInputs();
            view.hideProgress();
            view.onRegister();
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
