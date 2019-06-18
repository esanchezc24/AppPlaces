package com.example.appplaces.Model;

import android.support.annotation.NonNull;

import com.example.appplaces.View.login.LoginInterface;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginModel implements LoginInterface.Model {
    private LoginInterface.TaskListener taskListener;
    private FirebaseAuth firebaseAuth;

    public LoginModel(LoginInterface.TaskListener taskListener) {
        this.taskListener = taskListener;
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void doLogin(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                    taskListener.onSuccess();
                else
                    taskListener.onError(task.getException().getMessage());
            }
        });

    }
}
