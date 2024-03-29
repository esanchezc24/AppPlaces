package com.example.appplaces.model;

import android.support.annotation.NonNull;

import com.example.appplaces.entity.User;
import com.example.appplaces.view.register.RegisterInterface;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class RegisterModel implements RegisterInterface.Model {

    private RegisterInterface.TaskListener taskListener;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    public RegisterModel(RegisterInterface.TaskListener taskListener) {
        this.taskListener = taskListener;

    }

    @Override
    public void doRegister(User user) {
        firebaseAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    addUser(user, task.getResult().getUser().getUid());
                } else {
                    taskListener.onError(task.getException().getMessage());
                }
            }
        });
    }

    public void addUser(User user, String uid) {
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("name", user.getName());
        objectMap.put("email", user.getEmail());
        db.collection("users")
                .document(uid)
                .set(objectMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void avoid) {
                        taskListener.onSuccess();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        taskListener.onError(e.getMessage());
                    }
                });
    }
}
