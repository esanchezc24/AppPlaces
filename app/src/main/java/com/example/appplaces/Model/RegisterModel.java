package com.example.appplaces.Model;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.appplaces.View.login.register.RegisterInterface;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterModel implements RegisterInterface.Model {

    private RegisterInterface.TaskListener taskListener;
    private FirebaseAuth firebaseAuth;

    public RegisterModel(RegisterInterface.TaskListener taskListener) {
        this.taskListener = taskListener;
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void doRegister(String name, String email, String password) {
        Log.i("CUENTA ", email+" "+password);
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
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
